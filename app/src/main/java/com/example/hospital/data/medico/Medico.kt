package com.example.hospital.data.medico

import androidx.room.*
import com.example.hospital.data.especialidade.Especialidade
import java.io.Serializable

@Entity(foreignKeys = [ForeignKey(
    entity = Especialidade::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("especId"),
    onDelete = ForeignKey.RESTRICT
)]
)
data class Medico(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "especId") val especId : Int,
    @ColumnInfo(name = "first_name") val firstName: String? = null,
    @ColumnInfo(name = "last_name") val lastName: String? = null,
    @ColumnInfo(name = "telefone") val telefone : String? = null,
    @Embedded val address: Address?
) : Serializable{

    companion object{
        fun instance(especialidade: Especialidade, fullName : String, telefone: String? = null, address: Address? = null,id : Int? = 0) = Medico(
            id = id ?: 0,
            especId = especialidade.id,
            firstName = fullName.split(" ").first(),
            lastName = fullName.split(" ").last(),
            telefone = telefone,
            address = address
        )
    }
}

data class Address(
    val street: String?,
    val state: String?,
    val city: String?
){
    override fun toString(): String {
        return "$street - $state - $city"
    }
}

class MedicoEspecialidade (
    @Embedded val medico: Medico,
    @Relation(
        parentColumn = "especId",
        entityColumn = "id"
    )
    val especialidade: Especialidade,
)