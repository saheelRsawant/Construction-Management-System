//package com.company;

import java.util.*;
import java.io.*;

public class risingCity {
    public static void main(String[] args) throws FileNotFoundException {

        Construct_RedBlackTree redBlackTree = new Construct_RedBlackTree();

        ArrayList<Bldg_details> empty = new ArrayList<>();
        ArrayList<Bldg_details> two_parameter_output = new ArrayList<>();

        Construct_Heap bHeap = new Construct_Heap();

        Bldg_details current_bldg ;
        Bldg_details one_parameter_output;

        ArrayList<String> i_input = new ArrayList<>();
        ArrayList<int[]> bldg_val = new ArrayList<>();

        String s;
        int line_count = 0;
        int global_clock = 0;
        int five_days_counter = 0;
        int[] val;

        current_bldg = null;
        one_parameter_output = null;

        //Take an input
        File file = new File(args[0]);

        Scanner sc = new Scanner(file);

        //Read sc commands into a List
        while(sc.hasNextLine()){
            i_input.add(sc.nextLine());
        }

        for (int i = 0; i < i_input.size(); i++) {
            val = new int[4];
            s = i_input.get(i);

            int a =Integer.parseInt(s.substring(0, s.indexOf(":")));
            int b =Integer.parseInt(s.substring( s.indexOf("(") + 1, s.indexOf(",")) );
            int c =Integer.parseInt(s.substring( s.indexOf(",") + 1,s.indexOf(")")) );

            if(s.contains("Insert")) {
                //Insert value in val[3] as per Insert or Print Building
                val[0] = a;
                val[1] = b;
                val[2] = c;
                val[3] = 1;
                bldg_val.add(val);
            }
            else if(s.contains("PrintBuilding") && s.contains(",")){
                val[0] = a;
                val[1] = b;
                val[2] = c;
                val[3] = 0;
                bldg_val.add(val);
            }
            else{
                val[0] = a;
                val[1] = Integer.parseInt(s.substring( s.indexOf("(") + 1, s.indexOf(")")) );
                val[2] = -1;
                val[3] = 0;
                bldg_val.add(val);
            }
        }

        // Generate a Output file
        PrintStream outputFile = new PrintStream("Output_file.txt");
        PrintStream console = System.out;
        System.setOut(outputFile);

        while( i_input.size() > line_count || bHeap.Building_array[0] != null || five_days_counter > 0){

            if(bldg_val.size() > 0 && bldg_val.get(0)[3] == 1 && global_clock == bldg_val.get(0)[0])
            {
                val = bldg_val.remove(0);
                Bldg_details record = new Bldg_details(val[1], val[2]);
                //Insert the same record into Heap and Red-Black Tree
                bHeap.insert(record);
                redBlackTree.add(record);
                //Increment line counter
                line_count++;
            }
            else if(bldg_val.size() > 0 && bldg_val.get(0)[3] == 0 && global_clock == bldg_val.get(0)[0])
            {
                val = bldg_val.remove(0);
                if(val[2] < 0){
                    one_parameter_output = Construct_RedBlackTree.search(Construct_RedBlackTree.root, val[1]);
                    if(one_parameter_output == null)
                        System.out.print("(0,0,0)");
                    else
                    System.out.print(one_parameter_output);
                }
                else {
                    empty = new ArrayList<>();
                    two_parameter_output = Construct_RedBlackTree.searchRange(empty, Construct_RedBlackTree.root, val[1], val[2]);
                    if(two_parameter_output.isEmpty())
                        System.out.println("(0,0,0)");
                    for (int j = two_parameter_output.size()-1; j >=0 ; j--) {
                        if(j>1)
                            System.out.print(two_parameter_output.get(j)+",");
                        else
                            System.out.print(two_parameter_output.get(j));
                    }
                    System.out.println();
                }
                line_count++;
            }

            //Check whether the current_bldg is executed
            if( five_days_counter != 0) {
                if( five_days_counter == 5) {
                    if(current_bldg.get_executed_time() + 1 == current_bldg.get_total_time())
                    {
                        System.out.println( "("+ current_bldg.get_bldg_num() + "," + global_clock + ")" );
                        redBlackTree.remove(current_bldg); //remove record from Red-Black Tree
                    }
                    else {
                        current_bldg.set_executed_time(current_bldg.get_executed_time() + 1);
                        bHeap.insert(current_bldg);
                    }
                    five_days_counter = 0; // Reset counter to zero
                }
                else if(current_bldg.get_executed_time() + 1 == current_bldg.get_total_time()) {
                    System.out.println( "("+ current_bldg.get_bldg_num() + "," + global_clock + ")" );
                    redBlackTree.remove(current_bldg);
                    five_days_counter = 0;
                }
                else {
                    current_bldg.set_executed_time(current_bldg.get_executed_time() + 1);
                    five_days_counter++;
                }
            }

            if(five_days_counter == 0 && bHeap.Building_array[0] != null) {
                current_bldg = bHeap.removeRoot();
                five_days_counter++;
            }
            global_clock++; //Increment global counter after every step
        }
    }
}




