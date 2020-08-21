//package com.company;

public class Construct_Heap {
    private static final int Front_value = 0;
    Bldg_details[] Building_array;
    private int size_value;

    public Construct_Heap(){
        this.size_value = -1;
        this.Building_array = new Bldg_details[2000];
    }
    private int lNode (int index){
        return (index*2)+1;
    }
    private int rNode (int index){
        return (index*2) + 2;
    }
    private int pNode (int index){
        return (index-1)/2;
    }
    private void swap(int index1, int index2){
        Bldg_details tmp = Building_array[index1];
        Building_array[index1] = Building_array[index2];
        Building_array[index2] = tmp;
    }
    private boolean isLeaf(int index){
        if(index >= Building_array.length/2 && index <= Building_array.length)
            return true;
        else
            return false;
    }

    public void Heapify(int index) {
        if (!isLeaf(index)) {
            if ((Building_array[lNode(index)] != null &&
                    compare_funct(Building_array[lNode(index)], index))
                    || (Building_array[rNode(index)] != null &&
                    compare_funct(Building_array[rNode(index)], index))
            ) {
                if (Building_array[lNode(index)] == null) {
                    swap(index, rNode(index));
                    Heapify(rNode(index));
                } else if (Building_array[rNode(index)] == null) {
                    swap(index, lNode(index));
                    Heapify(lNode(index));
                } else {
                    if (compare_funct(Building_array[lNode(index)], rNode(index))) {
                        swap(index, lNode(index));
                        Heapify(lNode(index));
                    } else {
                        swap(index, rNode(index));
                        Heapify(rNode(index));
                    }
                }
            }
        }
    }
    public boolean compare_funct(Bldg_details bR, int index){
        if(Building_array[index].get_executed_time() > bR.get_executed_time())
            return true;
        if(Building_array[index].get_executed_time() < bR.get_executed_time() )
            return false;
        if(Building_array[index].get_bldg_num() < bR.get_bldg_num() )
            return false;
        return true;
    }

    public void insert(Bldg_details record){

        Building_array[++size_value] = record;
        int current = size_value;

        while( current>0 && compare_funct(Building_array[current], pNode(current) ) )  {
            {
                swap(current, pNode(current));
                current = pNode(current);
            }
        }
    }
    public void minHeap(){
        for (int i = (Building_array.length - 1)/2; i >= 1 ; i--) {
            Heapify(i);
        }
    }

    public Bldg_details removeRoot()
    {
        Bldg_details abc = null;
        if(Building_array[0] != null)
        {
            abc = Building_array[Front_value];
            Building_array[Front_value] = Building_array[size_value];
            Building_array[size_value--] = null;
            Heapify(Front_value);
        }
        return abc;
    }


}
