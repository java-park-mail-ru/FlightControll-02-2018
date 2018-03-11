package ru.technopark.flightcontrol.wrappers;

public class PaginateWrapper {
    private int page = 1;
    private int size = 1;

    public PaginateWrapper() {

    }
    public PaginateWrapper(int page, int size) {
        this.page = page;
        this.size = size;
    }
    public PaginateWrapper(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
