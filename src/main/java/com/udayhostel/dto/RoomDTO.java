package com.udayhostel.dto;

import jakarta.validation.constraints.Min;

public class RoomDTO {

    private int roomNo;

    @Min(value = 1, message = "Capacity must be greater than 0")
    private int capacity;

    @Min(value = 1, message = "Floor must be greater than 0")
    private int floor;

    public RoomDTO() {
    }

    public RoomDTO(int roomNo, int capacity, int floor) {
        this.roomNo = roomNo;
        this.capacity = capacity;
        this.floor = floor;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}