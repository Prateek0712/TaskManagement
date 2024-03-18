package com.TaskManagement.TaskManagement.comparators;

import com.TaskManagement.TaskManagement.ResponceDto.getAllTaskResp;

import java.util.Comparator;

public class compareByStatus implements Comparator<getAllTaskResp> {
    @Override
    public int compare(getAllTaskResp task1, getAllTaskResp task2) {
        // Define the order of status enums
        String[] statusOrder = {"pending", "inProgress", "completed"};

        // Get the index of the status enum for each task
        int index1 = getStatusIndex(String.valueOf(task1.getStatus()), statusOrder);
        int index2 = getStatusIndex(String.valueOf(task1.getStatus()), statusOrder);

        // Compare the indexes
        return Integer.compare(index1, index2);
    }

    // Helper method to get the index of a status in the statusOrder array
    private int getStatusIndex(String status, String[] statusOrder) {
        for (int i = 0; i < statusOrder.length; i++) {
            if (status.equalsIgnoreCase(statusOrder[i])) {
                return i;
            }
        }
        // If status is not found in statusOrder, return a high index
        return statusOrder.length;
    }

}
