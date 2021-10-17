package com.bitcoin.miningtest.core;

import com.bitcoin.miningtest.model.Transaction;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

import static com.bitcoin.miningtest.core.Utils.doubleDigestTwoBuffers;

@Log4j2
public class MerkleRootUtils {

    public Sha256Hash calculateMerkleRoot(List<Transaction> transactions) {
        List<byte[]> tree = buildMerkleTree(transactions);
        return new Sha256Hash(tree.get(tree.size() - 1));
    }

    private List<byte[]> buildMerkleTree(List<Transaction> transactions) {
        // The Merkle root is based on a tree of hashes calculated from the transactions:
        //
        //            root
        //             / \
        //            /   \
        //          A      B
        //         / \    / \
        //        t1 t2  t3 t4
        //
        // The tree is represented as a list: t1,t2,t3,t4,A,B,root where each entry is a hash.
        //
        // The hashing algorithm is double SHA-256. The leaves are a hash of the serialized contents of the
        // transaction. The interior nodes are hashes of the concenation of the two child hashes.
        //
        // This structure allows the creation of proof that a transaction was included into a block without having to
        // provide the full block contents. Instead, you can provide only a Merkle branch. For example to prove tx2 was
        // in a block you can just provide tx2, the hash(tx1) and B. Now the other party has everything they need to
        // derive the root, which can be checked against the block header. These proofs aren't used right now but
        // will be helpful later when we want to download partial block contents.
        //
        // Note that if the number of transactions is not a power of two the last tx is repeated to make it so (see
        // tx3 above). A tree with 5 transactions would look like this:
        //
        //                    root
        //                  /      \
        //                /          \
        //              1             6
        //            /  \          /   \
        //          2     3       4      5
        //         / \   / \    /  \   /  \
        //       t1 t2  t3 t4  t5 t5  t5  t5
        ArrayList<byte[]> tree = new ArrayList<byte[]>();
        // Start by adding all the hashes of the transactions as leaves of the tree.
        for (Transaction t : transactions) {
            tree.add(t.getHash().getBytes());
        }
        int levelOffset = 0;  // Offset in the list where the currently processed level starts.
        // Step through each level, stopping when we reach the root (levelSize == 1).
        for (int levelSize = transactions.size(); levelSize > 1; levelSize = (levelSize + 1) / 2) {
            // For each pair of nodes on that level:
            for (int left = 0; left < levelSize; left += 2) {
                // The right hand node can be the same as the left hand, in the case where we don't have enough
                // transactions to be a power of two.
                int right = Math.min(left + 1, levelSize - 1);
                byte[] leftBytes = Utils.reverseBytes(tree.get(levelOffset + left));
                byte[] rightBytes = Utils.reverseBytes(tree.get(levelOffset + right));
                tree.add(Utils.reverseBytes(doubleDigestTwoBuffers(leftBytes, 0, 32, rightBytes, 0, 32)));
            }
            // Move to the next level.
            levelOffset += levelSize;
        }
        return tree;
    }
}
