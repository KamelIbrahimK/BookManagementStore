package com.example.onlinebookstoremanagementsystem.Entities;

import com.example.onlinebookstoremanagementsystem.Enums.HistoryType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Data
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer historyId;
    private Integer customerId;
    private Integer bookId;
    @Enumerated(EnumType.STRING)
    private HistoryType historyType;

    public History( Integer customerId, Integer bookId, HistoryType historyType) {
        this.customerId = customerId;
        this.bookId = bookId;
        this.historyType = historyType;
    }



}
