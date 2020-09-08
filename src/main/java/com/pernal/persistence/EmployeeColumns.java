package com.pernal.persistence;

public enum EmployeeColumns {
    ID(1), NAME(2), SURNAME(3), GRADE(4), SALARY(5);

    private int index;

    EmployeeColumns(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
