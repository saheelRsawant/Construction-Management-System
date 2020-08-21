//package com.company;

import java.util.ArrayList;

public class Construct_RedBlackTree {
    static class Node {
        // Data value
        Bldg_details bldg_data_value;
        // Left child pointer
        Node l_child;
        // Right child pointer
        Node r_child;
        // Parent Node pointer
        Node parent_node;
        String color; // color of the node.

        public Node(Bldg_details data, String color) {
            this.bldg_data_value = data;
            this.l_child = null;
            this.r_child = null;
            this.parent_node = null;
            this.color = color;
        }
    }
    //Root Node
    static Node root;

    // Create a new Node.
    public Node makeNode(Bldg_details bR) {
        Node node = new Node(bR, "R");
        node.l_child = new Node(null, "B");
        node.r_child = new Node(null, "B");
        return node;
    }

    // Adding elements to the tree.
    public boolean add(Bldg_details bR) {
        Node node = makeNode(bR);
        if (root == null) {
            root = node;
            root.color = "B";
            return true;
        }

        Node temp = root;
        while (temp != null) {
            if (temp.bldg_data_value.get_bldg_num() == bR.get_bldg_num()) {
                return false;
            }
            if (temp.bldg_data_value.get_bldg_num() > bR.get_bldg_num()) {
                if (temp.l_child.bldg_data_value == null) {
                    temp.l_child = node;
                    node.parent_node = temp;
                    balance(node); // balance the tree.
                    return true;
                }
                temp = temp.l_child;
                continue;
            }
            if (temp.bldg_data_value.get_bldg_num() < bR.get_bldg_num()) {
                if (temp.r_child.bldg_data_value == null) {
                    temp.r_child = node;
                    node.parent_node = temp;
                    balance(node); // balance the tree.
                    return true;
                }
                temp = temp.r_child;
            }
        }
        return true;
    }

    // Remove elements from the tree.
    public void remove(Bldg_details data) {

        if (root == null) {
            return;
        }

        // Search a given element in Red Black tree.
        Node temp = root;
        while (temp.bldg_data_value != null) {
            if (temp.bldg_data_value == data) {
                break;
            }
            if (temp.bldg_data_value.get_bldg_num() >= data.get_bldg_num()) {
                temp = temp.l_child;
                continue;
            }
            if (temp.bldg_data_value.get_bldg_num() < data.get_bldg_num()) {
                temp = temp.r_child;
                continue;
            }
        }

        // If not found. then return.
        if (temp.bldg_data_value == null) {
            return;
        }

        // find the next greater number than the given data.
        Node next = findNext(temp);

        // swap the data values of given node and next greater number.
        Bldg_details t = temp.bldg_data_value;
        temp.bldg_data_value = next.bldg_data_value;
        next.bldg_data_value = t;

        // Removing the next node.
        Node parent = next.parent_node;
        if (parent == null) {
            if (next.l_child.bldg_data_value == null) {
                root = null;
            } else {
                root = next.l_child;
                next.parent_node = null;
                root.color = "B";
            }
            return;
        }

        if (parent.r_child == next) {
            parent.r_child = next.l_child;
        } else {
            parent.l_child = next.l_child;
        }
        next.l_child.parent_node = parent;
        String color = next.l_child.color + next.color;
        balance(next.l_child, color); // balance the tree.
    }

    //To balance the node after deletion
    private static void balance(Node node, String color) {

        // if node is Red-Black.
        if (node.parent_node == null || color.equals("BR") || color.equals("RB")) {
            node.color = "B";
            return;
        }

        Node parent = node.parent_node;

        // To locate the siblings of the given node.
        Node sibling = null;
        if (parent.l_child == node) {
            sibling = parent.r_child;
        } else {
            sibling = parent.l_child;
        }

        Node sibleft = sibling.l_child; // sibling's left node.
        Node sibright = sibling.r_child; // siblings right node.

        if(sibleft==null && sibright==null) {
            return;
        }

        // All siblings are black.
        if (sibling.color == "B" && sibleft.color == "B" && sibright.color == "B") {
            sibling.color = "R";
            node.color = "B";
            String c = parent.color + "B";
            balance(parent, c);
            return;
        }

        // If a sibling node is red.
        if (sibling.color == "R") {
            if (parent.r_child == sibling) {
                leftRotate(sibling);
            } else {
                rightRotate(sibling);
            }
            balance(node, color);
            return;
        }

        // If a sibling node is red but one of its children are red.
        if(sibleft==null) {
            return;
        }
        if (parent.l_child == sibling) {
            if (sibleft.color == "R") {
                rightRotate(sibling);
                sibleft.color = "B";
            } else {
                leftRotate(sibright);
                rightRotate(sibright);
                sibright.l_child.color = "B";
            }
            return;
        } else {
            if (sibright.color == "R") {
                leftRotate(sibling);
                sibright.color = "B";
            } else {
                rightRotate(sibleft);
                leftRotate(sibleft);
                sibleft.r_child.color = "B";
            }
            return;
        }
    }

    // To find the element that is greatest after the given node.
    private static Node findNext(Node node) {
        Node next = node.r_child;
        if (next.bldg_data_value == null) {
            return node;
        }
        while (next.l_child.bldg_data_value != null) {
            next = next.l_child;
        }
        return next;
    }

 // Balancing the tree
    public static void balance(Node node) {

        // If the current node is root node.
        if (node.parent_node == null) {
            root = node;
            root.color = "B";
            return;
        }

        // If the current node's parent color is black.
        if (node.parent_node.color == "B") {
            return;
        }

        // get the node's parent's sibling node.
        Node sibling = null;
        if (node.parent_node.parent_node.l_child == node.parent_node) {
            sibling = node.parent_node.parent_node.r_child;
        } else {
            sibling = node.parent_node.parent_node.l_child;
        }

        // if sibling color is red.
        if (sibling.color == "R") {
            node.parent_node.color = "B";
            sibling.color = "B";
            node.parent_node.parent_node.color = "R";
            balance(node.parent_node.parent_node);
            return;
        }

        // if sibling color is black.
        else {
            if (node.parent_node.l_child == node && node.parent_node.parent_node.l_child == node.parent_node) {
                rightRotate(node.parent_node);
                balance(node.parent_node);
                return;
            }
            if (node.parent_node.r_child == node && node.parent_node.parent_node.r_child == node.parent_node) {
                leftRotate(node.parent_node);
                balance(node.parent_node);
                return;
            }
            if (node.parent_node.r_child == node && node.parent_node.parent_node.l_child == node.parent_node) {
                leftRotate(node);
                rightRotate(node);
                balance(node);
                return;
            }
            if (node.parent_node.l_child == node && node.parent_node.parent_node.r_child == node.parent_node) {
                rightRotate(node);
                leftRotate(node);
                balance(node);
                return;
            }
        }
    }

    // Left Rotation on the Red-Black Tree.
    private static void leftRotate(Node node) {
        Node parent = node.parent_node;
        Node left = node.l_child;
        node.l_child = parent;
        parent.r_child = left;
        if (left != null) {
            left.parent_node = parent;
        }
        String c = parent.color;
        parent.color = node.color;
        node.color = c;
        Node gp = parent.parent_node;
        parent.parent_node = node;
        node.parent_node = gp;

        if (gp == null) {
            root = node;
            return;
        } else {
            if (gp.l_child == parent) {
                gp.l_child = node;
            } else {
                gp.r_child = node;
            }
        }
    }

    // Right Rotation on the Red-Black tree
    private static void rightRotate(Node node) {
        Node parent = node.parent_node;
        Node right = node.r_child;
        node.r_child = parent;
        parent.l_child = right;
        if (right != null) {
            right.parent_node = parent;
        }
        String c = parent.color;
        parent.color = node.color;
        node.color = c;
        Node gp = parent.parent_node;
        parent.parent_node = node;
        node.parent_node = gp;

        if (gp == null) {
            root = node;
            return;
        } else {
            if (gp.l_child == parent) {
                gp.l_child = node;
            } else {
                gp.r_child = node;
            }
        }
    }

    // function for PreOrder Traversal of the tree.
    private static void preOrder(Node node) {
        if (node.bldg_data_value == null) {
            return;
        }
        System.out.print(node.bldg_data_value + "-" + node.color + " ");
        preOrder(node.l_child);
        preOrder(node.r_child);
    }

    // Display the Red-Black tree.
    public static void display() {
        if (root == null) {
            System.out.println("Empty Tree");
            return;
        }
        System.out.print("Tree's PreOrder Traversal : ");
        preOrder(root);
        System.out.println();
    }

    public static Bldg_details search(Node node, int buildingNumber) {
        if(node == null || node.bldg_data_value == null) {
            return null;
        }
        if(node.bldg_data_value.get_bldg_num()==buildingNumber) {
            return node.bldg_data_value;
        }else if(node.bldg_data_value.get_bldg_num()<buildingNumber) {
            return search(node.r_child, buildingNumber);
        }else if(node.bldg_data_value.get_bldg_num()>buildingNumber) {
            return search(node.l_child, buildingNumber);
        }
        return null;
    }

    public static ArrayList<Bldg_details> searchRange(ArrayList<Bldg_details> list, Node node, int startBuilding, int endBuilding){
        if(node==null || node.bldg_data_value == null) {
            return list;
        }
        if(inRange(node.bldg_data_value.get_bldg_num(), startBuilding, endBuilding)) {

            list.add(node.bldg_data_value);
        }if(node.bldg_data_value.get_bldg_num() > startBuilding) {

            searchRange(list, node.l_child, startBuilding, endBuilding);
        }if(node.bldg_data_value.get_bldg_num() < endBuilding) {

            searchRange(list, node.r_child, startBuilding, endBuilding);
        }
        return list;
    }

    private static boolean inRange(int buildingNum, int startBuildNum, int endBuildNum) {
        if(buildingNum >= startBuildNum && buildingNum<=endBuildNum)
            return true;
        return false;
    }
}


