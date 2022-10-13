package com.example.hospital.domain.especialidade

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Especialidade(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "descricao") var descricao: String,
){
    constructor(descricao: String) :this(0, descricao)
    //TODO  Esse construtor so e possivel pq especialidade nao e DAta Class, isso e um problema ?
}