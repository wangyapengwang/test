package com.wyp.excelDemo;

/**
 * Class ExUtEntity
 * PackageName com.wyp.excelDemo
 * DATE 2019/11/11 9:31
 * Describe
 */
public class ExUtEntity {

    private int row;

    private int cell;

    private int value;



    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ExUtEntity() {
        super();
    }

    public ExUtEntity(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    public ExUtEntity(int row, int cell, int value) {
        this.row = row;
        this.cell = cell;
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    @Override
    public String toString() {
        return "ExUtEntity{" +
                "row=" + row +
                ", cell=" + cell +
                ", value=" + value +
                '}';
    }
}
