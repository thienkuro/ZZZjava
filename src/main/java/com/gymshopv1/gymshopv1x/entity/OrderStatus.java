package com.gymshopv1.gymshopv1x.entity;

public enum OrderStatus {
    Đã_đặt("Đã đặt"),
    Đã_giao("Đã giao"),
    Đã_hủy("Đã hủy");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label; // JPA sẽ dùng toString() nếu được override
    }
}
