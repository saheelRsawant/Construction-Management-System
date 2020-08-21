//package com.company;

public class Bldg_details {
    private int buildingNum;
    private int executed_time;
    private int total_time;

    public Bldg_details(int buildingNum, int total_time) {
        this.buildingNum = buildingNum;
        this.executed_time = 0;
        this.total_time = total_time;
    }

    public int get_bldg_num() {
        return buildingNum;
    }

    public int get_executed_time() {
        return executed_time; }

    public int get_total_time() {
        return total_time; }

    public void setBuildingNum(int buildingNum) {
        this.buildingNum = buildingNum; }

    public void set_executed_time(int executed_time) {
        this.executed_time = executed_time; }

    @Override
    public String toString() {
        return "("+ buildingNum + "," + (executed_time) + "," + total_time + ")";
    }
}


