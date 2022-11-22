package com.example.hospital.shared.data.medico

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MedicoFavorito(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "medicoId") val medicoId: Int,
    @ColumnInfo(name = "usuarioId") val usuarioId: String,
)