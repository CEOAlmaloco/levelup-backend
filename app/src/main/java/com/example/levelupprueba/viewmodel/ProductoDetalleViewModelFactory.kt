package com.example.levelupprueba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelupprueba.data.local.ReviewDao
import com.example.levelupprueba.model.producto.ProductoRepository

//el factory es como una fabrica q crea viewmodels con parametros personalizados
//pq por defecto los viewmodels no pueden recibir parametros en el constructor
class ProductoDetalleViewModelFactory(
    private val reviewDao: ReviewDao //recibimos el reviewDao para pasarselo al repository
) : ViewModelProvider.Factory { //heredamos de Factory para poder crear viewmodels
    
    @Suppress("UNCHECKED_CAST") //esto es para q no nos moleste el compilador con el casteo
    override fun <T : ViewModel> create(modelClass: Class<T>): T { //esta funcion crea el viewmodel
        if (modelClass.isAssignableFrom(ProductoDetalleViewModel::class.java)) { //verificamos q sea el viewmodel correcto
            //creamos el repository con el reviewDao y se lo pasamos al viewmodel
            val repository = ProductoRepository(reviewDao) //ahora el repository tiene acceso a SQLite
            return ProductoDetalleViewModel(repository) as T //retornamos el viewmodel con el repository configurado
        }
        throw IllegalArgumentException("Unknown ViewModel class") //si no es el viewmodel correcto tiramos error
    }
}
//ahora podemos crear el viewmodel con el factory y le pasamos el reviewDao desde MainActivity
//ejemplo: viewModel(factory = ProductoDetalleViewModelFactory(reviewDao))

