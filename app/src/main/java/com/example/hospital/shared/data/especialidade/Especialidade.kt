package com.example.hospital.shared.data.especialidade

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Especialidade(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "descricao") var descricao: String,
){
    override fun toString(): String = descricao
}