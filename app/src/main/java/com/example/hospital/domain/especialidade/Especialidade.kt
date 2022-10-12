package com.example.hospital.domain.especialidade

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Especialidade(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "descricao") var descricao: String,
)