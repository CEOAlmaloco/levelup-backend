23:46:08.413 [Test worker @kotlinx.coroutines.test runner#189] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for ProductoRepository name=#52
23:46:08.764 [Test worker @kotlinx.coroutines.test runner#189] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Producto name=child of #52#53
23:46:08.766 [Test worker @kotlinx.coroutines.test runner#189] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Review name=#54
23:46:08.824 [Test worker @kotlinx.coroutines.test runner#189] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Review name=#55
23:46:09.291 [Test worker @kotlinx.coroutines.test runner#189] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for List name=child of #52#56
23:46:09.300 [Test worker @kotlinx.coroutines.test runner#189] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for List name=child of #52#57

El producto debe estar cargado ==> expected: not <null>
org.opentest4j.AssertionFailedError: El producto debe estar cargado ==> expected: not <null>
	at org.junit.jupiter.api.AssertionFailureBuilder.build(AssertionFailureBuilder.java:152)
	at org.junit.jupiter.api.AssertionFailureBuilder.buildAndThrow(AssertionFailureBuilder.java:132)
	at org.junit.jupiter.api.AssertNotNull.failNull(AssertNotNull.java:49)
	at org.junit.jupiter.api.AssertNotNull.assertNotNull(AssertNotNull.java:35)
	at org.junit.jupiter.api.Assertions.assertNotNull(Assertions.java:312)
	at com.example.levelupprueba.viewmodel.ProductoDetalleViewModelTest$cargarProducto exitoso compone detalle con reviews y relacionados$1.invokeSuspend(ProductoDetalleViewModelTest.kt:60)
	at com.example.levelupprueba.viewmodel.ProductoDetalleViewModelTest$cargarProducto exitoso compone detalle con reviews y relacionados$1.invoke(ProductoDetalleViewModelTest.kt)
	at com.example.levelupprueba.viewmodel.ProductoDetalleViewModelTest$cargarProducto exitoso compone detalle con reviews y relacionados$1.invoke(ProductoDetalleViewModelTest.kt)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$1.invokeSuspend(TestBuilders.kt:316)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.test.TestDispatcher.processEvent$kotlinx_coroutines_test(TestDispatcher.kt:24)
	at kotlinx.coroutines.test.TestCoroutineScheduler.tryRunNextTaskUnless$kotlinx_coroutines_test(TestCoroutineScheduler.kt:99)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$workRunner$1.invokeSuspend(TestBuilders.kt:322)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:277)
	at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:95)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:69)
	at kotlinx.coroutines.BuildersKt.runBlocking(Unknown Source)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default(Builders.kt:48)
	at kotlinx.coroutines.BuildersKt.runBlocking$default(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersJvmKt.createTestResult(TestBuildersJvm.kt:10)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:310)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:168)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0$default(TestBuilders.kt:160)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0$default(Unknown Source)
	at com.example.levelupprueba.viewmodel.ProductoDetalleViewModelTest.cargarProducto exitoso compone detalle con reviews y relacionados(ProductoDetalleViewModelTest.kt:31)
	at java.base/java.lang.reflect.Method.invoke(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)

23:46:09.320 [Test worker @coroutine#196] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering Producto(id=PX, codigo=null, nombre=Producto X, descripcion=, precio=100000.0, precioConDescuentoBackend=null, imagenUrl=img, categoria=CONSOLA, categoriaNombre=null, subcategoria=MANDOS, subcategoriaNombre=null, rating=4.5, ratingPromedioBackend=null, disponible=true, destacado=false, enOferta=false, stock=5, imagenesUrls=[img], fabricante=null, distribuidor=null, descuento=null, reviews=[], productosRelacionados=[]) on ProductoRepository(#52).obtenerProductoPorId(PX, continuation {})
23:46:09.359 [Test worker @coroutine#196] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering [Review(#54), Review(#55)] on ProductoRepository(#52).obtenerReviews(PX, continuation {})
23:46:09.365 [Test worker @coroutine#196] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering [Producto(id=PY, codigo=null, nombre=Producto X, descripcion=, precio=100000.0, precioConDescuentoBackend=null, imagenUrl=img, categoria=CONSOLA, categoriaNombre=null, subcategoria=MANDOS, subcategoriaNombre=null, rating=4.5, ratingPromedioBackend=null, disponible=true, destacado=false, enOferta=false, stock=5, imagenesUrls=[img], fabricante=null, distribuidor=null, descuento=null, reviews=[], productosRelacionados=[])] on ProductoRepository(#52).obtenerProductosRelacionados(Producto(id=PX, codigo=null, nombre=Producto X, descripcion=, precio=100000.0, precioConDescuentoBackend=null, imagenUrl=img, categoria=CONSOLA, categoriaNombre=null, subcategoria=MANDOS, subcategoriaNombre=null, rating=4.5, ratingPromedioBackend=null, disponible=true, destacado=false, enOferta=false, stock=5, imagenesUrls=[img], fabricante=null, distribuidor=null, descuento=null, reviews=[], productosRelacionados=[]), continuation {})
23:46:09.372 [Test worker @kotlinx.coroutines.test runner#198] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for ProductoRepository name=#58
23:46:09.375 [Test worker @kotlinx.coroutines.test runner#198] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Producto name=child of #58#59
23:46:09.381 [Test worker @kotlinx.coroutines.test runner#198] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for List name=child of #58#60
23:46:09.386 [Test worker @kotlinx.coroutines.test runner#198] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for List name=child of #58#61
23:46:09.388 [Test worker @coroutine#205] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering Producto(id=PX, codigo=null, nombre=Producto X, descripcion=, precio=100000.0, precioConDescuentoBackend=null, imagenUrl=img, categoria=CONSOLA, categoriaNombre=null, subcategoria=MANDOS, subcategoriaNombre=null, rating=4.5, ratingPromedioBackend=null, disponible=true, destacado=false, enOferta=false, stock=5, imagenesUrls=[img], fabricante=null, distribuidor=null, descuento=null, reviews=[], productosRelacionados=[]) on ProductoRepository(#58).obtenerProductoPorId(PX, continuation {})
23:46:09.390 [Test worker @coroutine#205] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering [] on ProductoRepository(#58).obtenerReviews(PX, continuation {})
23:46:09.392 [Test worker @coroutine#205] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering [] on ProductoRepository(#58).obtenerProductosRelacionados(Producto(id=PX, codigo=null, nombre=Producto X, descripcion=, precio=100000.0, precioConDescuentoBackend=null, imagenUrl=img, categoria=CONSOLA, categoriaNombre=null, subcategoria=MANDOS, subcategoriaNombre=null, rating=4.5, ratingPromedioBackend=null, disponible=true, destacado=false, enOferta=false, stock=5, imagenesUrls=[img], fabricante=null, distribuidor=null, descuento=null, reviews=[], productosRelacionados=[]), continuation {})
23:46:09.398 [Test worker @kotlinx.coroutines.test runner#207] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for ProductoRepository name=#62
23:46:09.403 [Test worker @kotlinx.coroutines.test runner#207] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Producto name=child of #62#63

Debe haber un mensaje de error ==> expected: not <null>
org.opentest4j.AssertionFailedError: Debe haber un mensaje de error ==> expected: not <null>
	at org.junit.jupiter.api.AssertionFailureBuilder.build(AssertionFailureBuilder.java:152)
	at org.junit.jupiter.api.AssertionFailureBuilder.buildAndThrow(AssertionFailureBuilder.java:132)
	at org.junit.jupiter.api.AssertNotNull.failNull(AssertNotNull.java:49)
	at org.junit.jupiter.api.AssertNotNull.assertNotNull(AssertNotNull.java:35)
	at org.junit.jupiter.api.Assertions.assertNotNull(Assertions.java:312)
	at com.example.levelupprueba.viewmodel.ProductoDetalleViewModelTest$cargarProducto con error expone mensaje y deja producto null$1.invokeSuspend(ProductoDetalleViewModelTest.kt:91)
	at com.example.levelupprueba.viewmodel.ProductoDetalleViewModelTest$cargarProducto con error expone mensaje y deja producto null$1.invoke(ProductoDetalleViewModelTest.kt)
	at com.example.levelupprueba.viewmodel.ProductoDetalleViewModelTest$cargarProducto con error expone mensaje y deja producto null$1.invoke(ProductoDetalleViewModelTest.kt)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$1.invokeSuspend(TestBuilders.kt:316)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.test.TestDispatcher.processEvent$kotlinx_coroutines_test(TestDispatcher.kt:24)
	at kotlinx.coroutines.test.TestCoroutineScheduler.tryRunNextTaskUnless$kotlinx_coroutines_test(TestCoroutineScheduler.kt:99)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$workRunner$1.invokeSuspend(TestBuilders.kt:322)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:277)
	at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:95)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:69)
	at kotlinx.coroutines.BuildersKt.runBlocking(Unknown Source)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default(Builders.kt:48)
	at kotlinx.coroutines.BuildersKt.runBlocking$default(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersJvmKt.createTestResult(TestBuildersJvm.kt:10)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:310)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:168)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0$default(TestBuilders.kt:160)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0$default(Unknown Source)
	at com.example.levelupprueba.viewmodel.ProductoDetalleViewModelTest.cargarProducto con error expone mensaje y deja producto null(ProductoDetalleViewModelTest.kt:72)
	at java.base/java.lang.reflect.Method.invoke(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)

23:46:02.712 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Creating static mockk for Log
23:46:02.735 [Test worker] DEBUG io.mockk.proxy.jvm.StaticProxyMaker -- Transforming class android.util.Log for static method interception
23:46:05.231 [Test worker @kotlinx.coroutines.test runner#2] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for CarritoRepository name=#1
23:46:05.812 [Test worker @kotlinx.coroutines.test runner#2] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #1#2
23:46:05.927 [Test worker @coroutine#5] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering Carrito(id=null, usuarioId=null, estado=null, items=[CarritoItem(id=ITEM1, producto=Producto(id=P1, codigo=null, nombre=Producto Test, descripcion=Descripción test, precio=10000.0, precioConDescuentoBackend=null, imagenUrl=test.jpg, categoria=CONSOLA, categoriaNombre=null, subcategoria=MANDOS, subcategoriaNombre=null, rating=4.5, ratingPromedioBackend=null, disponible=true, destacado=false, enOferta=false, stock=10, imagenesUrls=[test.jpg], fabricante=null, distribuidor=null, descuento=null, reviews=[], productosRelacionados=[]), cantidad=2)], totalServidor=null) on CarritoRepository(#1).getCarrito(continuation {})
23:46:06.082 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Disposing static mockk for class android.util.Log
23:46:06.157 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Creating static mockk for Log
23:46:06.158 [Test worker] DEBUG io.mockk.proxy.jvm.StaticProxyMaker -- Transforming class android.util.Log for static method interception
23:46:06.249 [Test worker @kotlinx.coroutines.test runner#9] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for CarritoRepository name=#3
23:46:06.256 [Test worker @kotlinx.coroutines.test runner#9] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #3#4
23:46:06.269 [Test worker @kotlinx.coroutines.test runner#9] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #3#5
23:46:06.272 [Test worker @coroutine#15] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering Carrito(id=null, usuarioId=null, estado=null, items=[CarritoItem(id=ITEM1, producto=Producto(id=P1, codigo=null, nombre=Producto Test, descripcion=Descripción test, precio=10000.0, precioConDescuentoBackend=null, imagenUrl=test.jpg, categoria=CONSOLA, categoriaNombre=null, subcategoria=MANDOS, subcategoriaNombre=null, rating=4.5, ratingPromedioBackend=null, disponible=true, destacado=false, enOferta=false, stock=10, imagenesUrls=[test.jpg], fabricante=null, distribuidor=null, descuento=null, reviews=[], productosRelacionados=[]), cantidad=1)], totalServidor=null) on CarritoRepository(#3).getCarrito(continuation {})
23:46:06.299 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Disposing static mockk for class android.util.Log

El carrito debe estar vacío después de decrementar desde 1
Expected :true
Actual   :false
<Click to see difference>

org.opentest4j.AssertionFailedError: El carrito debe estar vacío después de decrementar desde 1 ==> expected: <true> but was: <false>
	at org.junit.jupiter.api.AssertionFailureBuilder.build(AssertionFailureBuilder.java:151)
	at org.junit.jupiter.api.AssertionFailureBuilder.buildAndThrow(AssertionFailureBuilder.java:132)
	at org.junit.jupiter.api.AssertTrue.failNotTrue(AssertTrue.java:63)
	at org.junit.jupiter.api.AssertTrue.assertTrue(AssertTrue.java:36)
	at org.junit.jupiter.api.Assertions.assertTrue(Assertions.java:214)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onDecrement desde 1 elimina item (regla 0 elimina)$1.invokeSuspend(CarritoViewModelTest.kt:369)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onDecrement desde 1 elimina item (regla 0 elimina)$1.invoke(CarritoViewModelTest.kt)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onDecrement desde 1 elimina item (regla 0 elimina)$1.invoke(CarritoViewModelTest.kt)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$1.invokeSuspend(TestBuilders.kt:316)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.test.TestDispatcher.processEvent$kotlinx_coroutines_test(TestDispatcher.kt:24)
	at kotlinx.coroutines.test.TestCoroutineScheduler.tryRunNextTaskUnless$kotlinx_coroutines_test(TestCoroutineScheduler.kt:99)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$workRunner$1.invokeSuspend(TestBuilders.kt:322)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:277)
	at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:95)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:69)
	at kotlinx.coroutines.BuildersKt.runBlocking(Unknown Source)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default(Builders.kt:48)
	at kotlinx.coroutines.BuildersKt.runBlocking$default(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersJvmKt.createTestResult(TestBuildersJvm.kt:10)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:310)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:168)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0$default(TestBuilders.kt:160)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0$default(Unknown Source)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest.onDecrement desde 1 elimina item (regla 0 elimina)(CarritoViewModelTest.kt:335)
	at java.base/java.lang.reflect.Method.invoke(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)

23:46:06.350 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Creating static mockk for Log
23:46:06.351 [Test worker] DEBUG io.mockk.proxy.jvm.StaticProxyMaker -- Transforming class android.util.Log for static method interception
23:46:06.480 [Test worker @kotlinx.coroutines.test runner#20] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for CarritoRepository name=#6
23:46:06.488 [Test worker @kotlinx.coroutines.test runner#20] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #6#7
23:46:06.491 [Test worker @coroutine#23] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering Carrito(id=null, usuarioId=null, estado=null, items=[], totalServidor=null) on CarritoRepository(#6).getCarrito(continuation {})
23:46:06.534 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Disposing static mockk for class android.util.Log
23:46:06.591 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Creating static mockk for Log
23:46:06.592 [Test worker] DEBUG io.mockk.proxy.jvm.StaticProxyMaker -- Transforming class android.util.Log for static method interception
23:46:06.672 [Test worker @kotlinx.coroutines.test runner#29] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for CarritoRepository name=#8
23:46:06.676 [Test worker @kotlinx.coroutines.test runner#29] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #8#9
23:46:06.682 [Test worker @kotlinx.coroutines.test runner#29] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #8#10
23:46:06.684 [Test worker @coroutine#34] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering Carrito(id=null, usuarioId=null, estado=null, items=[CarritoItem(id=ITEM1, producto=Producto(id=P1, codigo=null, nombre=Producto Test, descripcion=Descripción test, precio=10000.0, precioConDescuentoBackend=null, imagenUrl=test.jpg, categoria=CONSOLA, categoriaNombre=null, subcategoria=MANDOS, subcategoriaNombre=null, rating=4.5, ratingPromedioBackend=null, disponible=true, destacado=false, enOferta=false, stock=10, imagenesUrls=[test.jpg], fabricante=null, distribuidor=null, descuento=null, reviews=[], productosRelacionados=[]), cantidad=2)], totalServidor=null) on CarritoRepository(#8).getCarrito(continuation {})
23:46:06.691 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Disposing static mockk for class android.util.Log

Expected :true
Actual   :false
<Click to see difference>

org.opentest4j.AssertionFailedError: expected: <true> but was: <false>
	at org.junit.jupiter.api.AssertionFailureBuilder.build(AssertionFailureBuilder.java:151)
	at org.junit.jupiter.api.AssertionFailureBuilder.buildAndThrow(AssertionFailureBuilder.java:132)
	at org.junit.jupiter.api.AssertTrue.failNotTrue(AssertTrue.java:63)
	at org.junit.jupiter.api.AssertTrue.assertTrue(AssertTrue.java:36)
	at org.junit.jupiter.api.AssertTrue.assertTrue(AssertTrue.java:31)
	at org.junit.jupiter.api.Assertions.assertTrue(Assertions.java:183)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onCheckout exitoso limpia carrito$1.invokeSuspend(CarritoViewModelTest.kt:328)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onCheckout exitoso limpia carrito$1.invoke(CarritoViewModelTest.kt)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onCheckout exitoso limpia carrito$1.invoke(CarritoViewModelTest.kt)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$1.invokeSuspend(TestBuilders.kt:316)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.test.TestDispatcher.processEvent$kotlinx_coroutines_test(TestDispatcher.kt:24)
	at kotlinx.coroutines.test.TestCoroutineScheduler.tryRunNextTaskUnless$kotlinx_coroutines_test(TestCoroutineScheduler.kt:99)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$workRunner$1.invokeSuspend(TestBuilders.kt:322)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:277)
	at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:95)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:69)
	at kotlinx.coroutines.BuildersKt.runBlocking(Unknown Source)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default(Builders.kt:48)
	at kotlinx.coroutines.BuildersKt.runBlocking$default(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersJvmKt.createTestResult(TestBuildersJvm.kt:10)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:310)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:168)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0$default(TestBuilders.kt:160)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0$default(Unknown Source)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest.onCheckout exitoso limpia carrito(CarritoViewModelTest.kt:300)
	at java.base/java.lang.reflect.Method.invoke(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)

23:46:06.728 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Creating static mockk for Log
23:46:06.728 [Test worker] DEBUG io.mockk.proxy.jvm.StaticProxyMaker -- Transforming class android.util.Log for static method interception
23:46:06.790 [Test worker @kotlinx.coroutines.test runner#39] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for CarritoRepository name=#11
23:46:06.795 [Test worker @kotlinx.coroutines.test runner#39] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #11#12
23:46:06.801 [Test worker @kotlinx.coroutines.test runner#39] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #11#13
23:46:06.803 [Test worker @coroutine#45] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering Carrito(id=null, usuarioId=null, estado=null, items=[], totalServidor=null) on CarritoRepository(#11).getCarrito(continuation {})
23:46:06.808 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Disposing static mockk for class android.util.Log

Expected :1
Actual   :0
<Click to see difference>

org.opentest4j.AssertionFailedError: expected: <1> but was: <0>
	at org.junit.jupiter.api.AssertionFailureBuilder.build(AssertionFailureBuilder.java:151)
	at org.junit.jupiter.api.AssertionFailureBuilder.buildAndThrow(AssertionFailureBuilder.java:132)
	at org.junit.jupiter.api.AssertEquals.failNotEqual(AssertEquals.java:197)
	at org.junit.jupiter.api.AssertEquals.assertEquals(AssertEquals.java:150)
	at org.junit.jupiter.api.AssertEquals.assertEquals(AssertEquals.java:145)
	at org.junit.jupiter.api.Assertions.assertEquals(Assertions.java:531)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onAgregar actualiza carrito y limpia error$1.invokeSuspend(CarritoViewModelTest.kt:122)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onAgregar actualiza carrito y limpia error$1.invoke(CarritoViewModelTest.kt)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onAgregar actualiza carrito y limpia error$1.invoke(CarritoViewModelTest.kt)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$1.invokeSuspend(TestBuilders.kt:316)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.test.TestDispatcher.processEvent$kotlinx_coroutines_test(TestDispatcher.kt:24)
	at kotlinx.coroutines.test.TestCoroutineScheduler.tryRunNextTaskUnless$kotlinx_coroutines_test(TestCoroutineScheduler.kt:99)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$workRunner$1.invokeSuspend(TestBuilders.kt:322)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:277)
	at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:95)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:69)
	at kotlinx.coroutines.BuildersKt.runBlocking(Unknown Source)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default(Builders.kt:48)
	at kotlinx.coroutines.BuildersKt.runBlocking$default(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersJvmKt.createTestResult(TestBuildersJvm.kt:10)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:310)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:168)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0$default(TestBuilders.kt:160)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0$default(Unknown Source)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest.onAgregar actualiza carrito y limpia error(CarritoViewModelTest.kt:95)
	at java.base/java.lang.reflect.Method.invoke(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)

23:46:06.844 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Creating static mockk for Log
23:46:06.844 [Test worker] DEBUG io.mockk.proxy.jvm.StaticProxyMaker -- Transforming class android.util.Log for static method interception
23:46:06.915 [Test worker @kotlinx.coroutines.test runner#50] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for CarritoRepository name=#14
23:46:06.919 [Test worker @kotlinx.coroutines.test runner#50] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #14#15
23:46:06.926 [Test worker @kotlinx.coroutines.test runner#50] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #14#16
23:46:06.929 [Test worker @coroutine#56] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering Carrito(id=null, usuarioId=null, estado=null, items=[CarritoItem(id=ITEM1, producto=Producto(id=P1, codigo=null, nombre=Producto Test, descripcion=Descripción test, precio=10000.0, precioConDescuentoBackend=null, imagenUrl=test.jpg, categoria=CONSOLA, categoriaNombre=null, subcategoria=MANDOS, subcategoriaNombre=null, rating=4.5, ratingPromedioBackend=null, disponible=true, destacado=false, enOferta=false, stock=2, imagenesUrls=[test.jpg], fabricante=null, distribuidor=null, descuento=null, reviews=[], productosRelacionados=[]), cantidad=2)], totalServidor=null) on CarritoRepository(#14).getCarrito(continuation {})
23:46:06.934 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Disposing static mockk for class android.util.Log

Debe haber un mensaje de error ==> expected: not <null>
org.opentest4j.AssertionFailedError: Debe haber un mensaje de error ==> expected: not <null>
	at org.junit.jupiter.api.AssertionFailureBuilder.build(AssertionFailureBuilder.java:152)
	at org.junit.jupiter.api.AssertionFailureBuilder.buildAndThrow(AssertionFailureBuilder.java:132)
	at org.junit.jupiter.api.AssertNotNull.failNull(AssertNotNull.java:49)
	at org.junit.jupiter.api.AssertNotNull.assertNotNull(AssertNotNull.java:35)
	at org.junit.jupiter.api.Assertions.assertNotNull(Assertions.java:312)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onIncrement no supera stock mantiene cantidad y expone error$1.invokeSuspend(CarritoViewModelTest.kt:511)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onIncrement no supera stock mantiene cantidad y expone error$1.invoke(CarritoViewModelTest.kt)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onIncrement no supera stock mantiene cantidad y expone error$1.invoke(CarritoViewModelTest.kt)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$1.invokeSuspend(TestBuilders.kt:316)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.test.TestDispatcher.processEvent$kotlinx_coroutines_test(TestDispatcher.kt:24)
	at kotlinx.coroutines.test.TestCoroutineScheduler.tryRunNextTaskUnless$kotlinx_coroutines_test(TestCoroutineScheduler.kt:99)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$workRunner$1.invokeSuspend(TestBuilders.kt:322)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:277)
	at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:95)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:69)
	at kotlinx.coroutines.BuildersKt.runBlocking(Unknown Source)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default(Builders.kt:48)
	at kotlinx.coroutines.BuildersKt.runBlocking$default(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersJvmKt.createTestResult(TestBuildersJvm.kt:10)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:310)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:168)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0$default(TestBuilders.kt:160)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0$default(Unknown Source)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest.onIncrement no supera stock mantiene cantidad y expone error(CarritoViewModelTest.kt:477)
	at java.base/java.lang.reflect.Method.invoke(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)

23:46:06.970 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Creating static mockk for Log
23:46:06.970 [Test worker] DEBUG io.mockk.proxy.jvm.StaticProxyMaker -- Transforming class android.util.Log for static method interception
23:46:07.033 [Test worker @kotlinx.coroutines.test runner#61] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for CarritoRepository name=#17
23:46:07.040 [Test worker @kotlinx.coroutines.test runner#61] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #17#18
23:46:07.044 [Test worker @kotlinx.coroutines.test runner#61] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #17#19
23:46:07.045 [Test worker @coroutine#66] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering Carrito(id=null, usuarioId=null, estado=null, items=[CarritoItem(id=ITEM1, producto=Producto(id=P1, codigo=null, nombre=Producto Test, descripcion=Descripción test, precio=10000.0, precioConDescuentoBackend=null, imagenUrl=test.jpg, categoria=CONSOLA, categoriaNombre=null, subcategoria=MANDOS, subcategoriaNombre=null, rating=4.5, ratingPromedioBackend=null, disponible=true, destacado=false, enOferta=false, stock=10, imagenesUrls=[test.jpg], fabricante=null, distribuidor=null, descuento=null, reviews=[], productosRelacionados=[]), cantidad=1)], totalServidor=null) on CarritoRepository(#17).getCarrito(continuation {})
23:46:07.050 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Disposing static mockk for class android.util.Log

Expected :fallo eliminar
Actual   :null
<Click to see difference>

org.opentest4j.AssertionFailedError: expected: <fallo eliminar> but was: <null>
	at org.junit.jupiter.api.AssertionFailureBuilder.build(AssertionFailureBuilder.java:151)
	at org.junit.jupiter.api.AssertionFailureBuilder.buildAndThrow(AssertionFailureBuilder.java:132)
	at org.junit.jupiter.api.AssertEquals.failNotEqual(AssertEquals.java:197)
	at org.junit.jupiter.api.AssertEquals.assertEquals(AssertEquals.java:182)
	at org.junit.jupiter.api.AssertEquals.assertEquals(AssertEquals.java:177)
	at org.junit.jupiter.api.Assertions.assertEquals(Assertions.java:1145)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onEliminar captura excepcion y mantiene carrito$1.invokeSuspend(CarritoViewModelTest.kt:437)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onEliminar captura excepcion y mantiene carrito$1.invoke(CarritoViewModelTest.kt)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onEliminar captura excepcion y mantiene carrito$1.invoke(CarritoViewModelTest.kt)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$1.invokeSuspend(TestBuilders.kt:316)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.test.TestDispatcher.processEvent$kotlinx_coroutines_test(TestDispatcher.kt:24)
	at kotlinx.coroutines.test.TestCoroutineScheduler.tryRunNextTaskUnless$kotlinx_coroutines_test(TestCoroutineScheduler.kt:99)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$workRunner$1.invokeSuspend(TestBuilders.kt:322)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:277)
	at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:95)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:69)
	at kotlinx.coroutines.BuildersKt.runBlocking(Unknown Source)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default(Builders.kt:48)
	at kotlinx.coroutines.BuildersKt.runBlocking$default(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersJvmKt.createTestResult(TestBuildersJvm.kt:10)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:310)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:168)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0$default(TestBuilders.kt:160)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0$default(Unknown Source)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest.onEliminar captura excepcion y mantiene carrito(CarritoViewModelTest.kt:411)
	at java.base/java.lang.reflect.Method.invoke(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)

23:46:07.083 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Creating static mockk for Log
23:46:07.084 [Test worker] DEBUG io.mockk.proxy.jvm.StaticProxyMaker -- Transforming class android.util.Log for static method interception
23:46:07.145 [Test worker @kotlinx.coroutines.test runner#71] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for CarritoRepository name=#20
23:46:07.148 [Test worker @kotlinx.coroutines.test runner#71] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #20#21
23:46:07.153 [Test worker @kotlinx.coroutines.test runner#71] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #20#22
23:46:07.156 [Test worker @coroutine#77] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering Carrito(id=null, usuarioId=null, estado=null, items=[], totalServidor=null) on CarritoRepository(#20).getCarrito(continuation {})
23:46:07.158 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Disposing static mockk for class android.util.Log

Expected :1
Actual   :0
<Click to see difference>

org.opentest4j.AssertionFailedError: expected: <1> but was: <0>
	at org.junit.jupiter.api.AssertionFailureBuilder.build(AssertionFailureBuilder.java:151)
	at org.junit.jupiter.api.AssertionFailureBuilder.buildAndThrow(AssertionFailureBuilder.java:132)
	at org.junit.jupiter.api.AssertEquals.failNotEqual(AssertEquals.java:197)
	at org.junit.jupiter.api.AssertEquals.assertEquals(AssertEquals.java:150)
	at org.junit.jupiter.api.AssertEquals.assertEquals(AssertEquals.java:145)
	at org.junit.jupiter.api.Assertions.assertEquals(Assertions.java:531)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onAgregar dos veces mismo producto acumula cantidades$1.invokeSuspend(CarritoViewModelTest.kt:287)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onAgregar dos veces mismo producto acumula cantidades$1.invoke(CarritoViewModelTest.kt)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onAgregar dos veces mismo producto acumula cantidades$1.invoke(CarritoViewModelTest.kt)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$1.invokeSuspend(TestBuilders.kt:316)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.test.TestDispatcher.processEvent$kotlinx_coroutines_test(TestDispatcher.kt:24)
	at kotlinx.coroutines.test.TestCoroutineScheduler.tryRunNextTaskUnless$kotlinx_coroutines_test(TestCoroutineScheduler.kt:99)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$workRunner$1.invokeSuspend(TestBuilders.kt:322)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:277)
	at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:95)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:69)
	at kotlinx.coroutines.BuildersKt.runBlocking(Unknown Source)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default(Builders.kt:48)
	at kotlinx.coroutines.BuildersKt.runBlocking$default(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersJvmKt.createTestResult(TestBuildersJvm.kt:10)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:310)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:168)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0$default(TestBuilders.kt:160)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0$default(Unknown Source)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest.onAgregar dos veces mismo producto acumula cantidades(CarritoViewModelTest.kt:257)
	at java.base/java.lang.reflect.Method.invoke(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)

23:46:07.192 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Creating static mockk for Log
23:46:07.193 [Test worker] DEBUG io.mockk.proxy.jvm.StaticProxyMaker -- Transforming class android.util.Log for static method interception
23:46:07.245 [Test worker @kotlinx.coroutines.test runner#82] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for CarritoRepository name=#23
23:46:07.249 [Test worker @kotlinx.coroutines.test runner#82] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #23#24
23:46:07.255 [Test worker @kotlinx.coroutines.test runner#82] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #23#25
23:46:07.256 [Test worker @coroutine#88] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering Carrito(id=null, usuarioId=null, estado=null, items=[CarritoItem(id=ITEM1, producto=Producto(id=P1, codigo=null, nombre=Producto Test, descripcion=Descripción test, precio=10000.0, precioConDescuentoBackend=null, imagenUrl=test.jpg, categoria=CONSOLA, categoriaNombre=null, subcategoria=MANDOS, subcategoriaNombre=null, rating=4.5, ratingPromedioBackend=null, disponible=true, destacado=false, enOferta=false, stock=1, imagenesUrls=[test.jpg], fabricante=null, distribuidor=null, descuento=null, reviews=[], productosRelacionados=[]), cantidad=1)], totalServidor=null) on CarritoRepository(#23).getCarrito(continuation {})
23:46:07.259 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Disposing static mockk for class android.util.Log

Expected :true
Actual   :false
<Click to see difference>

org.opentest4j.AssertionFailedError: expected: <true> but was: <false>
	at org.junit.jupiter.api.AssertionFailureBuilder.build(AssertionFailureBuilder.java:151)
	at org.junit.jupiter.api.AssertionFailureBuilder.buildAndThrow(AssertionFailureBuilder.java:132)
	at org.junit.jupiter.api.AssertTrue.failNotTrue(AssertTrue.java:63)
	at org.junit.jupiter.api.AssertTrue.assertTrue(AssertTrue.java:36)
	at org.junit.jupiter.api.AssertTrue.assertTrue(AssertTrue.java:31)
	at org.junit.jupiter.api.Assertions.assertTrue(Assertions.java:183)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$si repo reporta stock agotado al actualizar elimina item (sin error)$1.invokeSuspend(CarritoViewModelTest.kt:551)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$si repo reporta stock agotado al actualizar elimina item (sin error)$1.invoke(CarritoViewModelTest.kt)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$si repo reporta stock agotado al actualizar elimina item (sin error)$1.invoke(CarritoViewModelTest.kt)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$1.invokeSuspend(TestBuilders.kt:316)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.test.TestDispatcher.processEvent$kotlinx_coroutines_test(TestDispatcher.kt:24)
	at kotlinx.coroutines.test.TestCoroutineScheduler.tryRunNextTaskUnless$kotlinx_coroutines_test(TestCoroutineScheduler.kt:99)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$workRunner$1.invokeSuspend(TestBuilders.kt:322)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:277)
	at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:95)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:69)
	at kotlinx.coroutines.BuildersKt.runBlocking(Unknown Source)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default(Builders.kt:48)
	at kotlinx.coroutines.BuildersKt.runBlocking$default(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersJvmKt.createTestResult(TestBuildersJvm.kt:10)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:310)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:168)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0$default(TestBuilders.kt:160)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0$default(Unknown Source)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest.si repo reporta stock agotado al actualizar elimina item (sin error)(CarritoViewModelTest.kt:522)
	at java.base/java.lang.reflect.Method.invoke(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)

23:46:07.291 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Creating static mockk for Log
23:46:07.291 [Test worker] DEBUG io.mockk.proxy.jvm.StaticProxyMaker -- Transforming class android.util.Log for static method interception
23:46:07.350 [Test worker @kotlinx.coroutines.test runner#93] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for CarritoRepository name=#26
23:46:07.353 [Test worker @kotlinx.coroutines.test runner#93] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #26#27
23:46:07.356 [Test worker @kotlinx.coroutines.test runner#93] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #26#28
23:46:07.358 [Test worker @coroutine#98] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering Carrito(id=null, usuarioId=null, estado=null, items=[], totalServidor=null) on CarritoRepository(#26).getCarrito(continuation {})
23:46:07.361 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Disposing static mockk for class android.util.Log

Expected :Fallo backend
Actual   :null
<Click to see difference>

org.opentest4j.AssertionFailedError: expected: <Fallo backend> but was: <null>
	at org.junit.jupiter.api.AssertionFailureBuilder.build(AssertionFailureBuilder.java:151)
	at org.junit.jupiter.api.AssertionFailureBuilder.buildAndThrow(AssertionFailureBuilder.java:132)
	at org.junit.jupiter.api.AssertEquals.failNotEqual(AssertEquals.java:197)
	at org.junit.jupiter.api.AssertEquals.assertEquals(AssertEquals.java:182)
	at org.junit.jupiter.api.AssertEquals.assertEquals(AssertEquals.java:177)
	at org.junit.jupiter.api.Assertions.assertEquals(Assertions.java:1145)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onCheckout captura excepcion y expone mensaje de error$1.invokeSuspend(CarritoViewModelTest.kt:174)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onCheckout captura excepcion y expone mensaje de error$1.invoke(CarritoViewModelTest.kt)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onCheckout captura excepcion y expone mensaje de error$1.invoke(CarritoViewModelTest.kt)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$1.invokeSuspend(TestBuilders.kt:316)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.test.TestDispatcher.processEvent$kotlinx_coroutines_test(TestDispatcher.kt:24)
	at kotlinx.coroutines.test.TestCoroutineScheduler.tryRunNextTaskUnless$kotlinx_coroutines_test(TestCoroutineScheduler.kt:99)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$workRunner$1.invokeSuspend(TestBuilders.kt:322)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:277)
	at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:95)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:69)
	at kotlinx.coroutines.BuildersKt.runBlocking(Unknown Source)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default(Builders.kt:48)
	at kotlinx.coroutines.BuildersKt.runBlocking$default(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersJvmKt.createTestResult(TestBuildersJvm.kt:10)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:310)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:168)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0$default(TestBuilders.kt:160)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0$default(Unknown Source)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest.onCheckout captura excepcion y expone mensaje de error(CarritoViewModelTest.kt:158)
	at java.base/java.lang.reflect.Method.invoke(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)

23:46:07.394 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Creating static mockk for Log
23:46:07.394 [Test worker] DEBUG io.mockk.proxy.jvm.StaticProxyMaker -- Transforming class android.util.Log for static method interception
23:46:07.447 [Test worker @kotlinx.coroutines.test runner#103] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for CarritoRepository name=#29
23:46:07.452 [Test worker @kotlinx.coroutines.test runner#103] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #29#30
23:46:07.454 [Test worker @coroutine#106] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering Carrito(id=null, usuarioId=null, estado=null, items=[CarritoItem(id=I1, producto=Producto(id=P1, codigo=null, nombre=Sin desc, descripcion=Descripción test, precio=10000.0, precioConDescuentoBackend=null, imagenUrl=test.jpg, categoria=CONSOLA, categoriaNombre=null, subcategoria=MANDOS, subcategoriaNombre=null, rating=4.5, ratingPromedioBackend=null, disponible=true, destacado=false, enOferta=false, stock=10, imagenesUrls=[test.jpg], fabricante=null, distribuidor=null, descuento=null, reviews=[], productosRelacionados=[]), cantidad=2), CarritoItem(id=I2, producto=Producto(id=P2, codigo=null, nombre=Con 25%, descripcion=Descripción test, precio=40000.0, precioConDescuentoBackend=null, imagenUrl=test.jpg, categoria=CONSOLA, categoriaNombre=null, subcategoria=MANDOS, subcategoriaNombre=null, rating=4.5, ratingPromedioBackend=null, disponible=true, destacado=false, enOferta=false, stock=10, imagenesUrls=[test.jpg], fabricante=null, distribuidor=null, descuento=25, reviews=[], productosRelacionados=[]), cantidad=1)], totalServidor=null) on CarritoRepository(#29).getCarrito(continuation {})
23:46:07.458 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Disposing static mockk for class android.util.Log
23:46:07.490 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Creating static mockk for Log
23:46:07.491 [Test worker] DEBUG io.mockk.proxy.jvm.StaticProxyMaker -- Transforming class android.util.Log for static method interception
23:46:07.542 [Test worker @kotlinx.coroutines.test runner#110] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for CarritoRepository name=#31
23:46:07.546 [Test worker @kotlinx.coroutines.test runner#110] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #31#32
23:46:07.550 [Test worker @kotlinx.coroutines.test runner#110] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #31#33
23:46:07.555 [Test worker @coroutine#116] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering Carrito(id=null, usuarioId=null, estado=null, items=[CarritoItem(id=ITEM1, producto=Producto(id=P1, codigo=null, nombre=Producto Test, descripcion=Descripción test, precio=10000.0, precioConDescuentoBackend=null, imagenUrl=test.jpg, categoria=CONSOLA, categoriaNombre=null, subcategoria=MANDOS, subcategoriaNombre=null, rating=4.5, ratingPromedioBackend=null, disponible=true, destacado=false, enOferta=false, stock=10, imagenesUrls=[test.jpg], fabricante=null, distribuidor=null, descuento=null, reviews=[], productosRelacionados=[]), cantidad=1)], totalServidor=null) on CarritoRepository(#31).getCarrito(continuation {})
23:46:07.557 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Disposing static mockk for class android.util.Log

Expected :fallo incrementar
Actual   :null
<Click to see difference>

org.opentest4j.AssertionFailedError: expected: <fallo incrementar> but was: <null>
	at org.junit.jupiter.api.AssertionFailureBuilder.build(AssertionFailureBuilder.java:151)
	at org.junit.jupiter.api.AssertionFailureBuilder.buildAndThrow(AssertionFailureBuilder.java:132)
	at org.junit.jupiter.api.AssertEquals.failNotEqual(AssertEquals.java:197)
	at org.junit.jupiter.api.AssertEquals.assertEquals(AssertEquals.java:182)
	at org.junit.jupiter.api.AssertEquals.assertEquals(AssertEquals.java:177)
	at org.junit.jupiter.api.Assertions.assertEquals(Assertions.java:1145)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onIncrement en error mantiene carrito, setea mensaje y deja loading false$1.invokeSuspend(CarritoViewModelTest.kt:674)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onIncrement en error mantiene carrito, setea mensaje y deja loading false$1.invoke(CarritoViewModelTest.kt)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onIncrement en error mantiene carrito, setea mensaje y deja loading false$1.invoke(CarritoViewModelTest.kt)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$1.invokeSuspend(TestBuilders.kt:316)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.test.TestDispatcher.processEvent$kotlinx_coroutines_test(TestDispatcher.kt:24)
	at kotlinx.coroutines.test.TestCoroutineScheduler.tryRunNextTaskUnless$kotlinx_coroutines_test(TestCoroutineScheduler.kt:99)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$workRunner$1.invokeSuspend(TestBuilders.kt:322)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:277)
	at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:95)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:69)
	at kotlinx.coroutines.BuildersKt.runBlocking(Unknown Source)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default(Builders.kt:48)
	at kotlinx.coroutines.BuildersKt.runBlocking$default(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersJvmKt.createTestResult(TestBuildersJvm.kt:10)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:310)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:168)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0$default(TestBuilders.kt:160)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0$default(Unknown Source)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest.onIncrement en error mantiene carrito, setea mensaje y deja loading false(CarritoViewModelTest.kt:651)
	at java.base/java.lang.reflect.Method.invoke(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)

23:46:07.588 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Creating static mockk for Log
23:46:07.588 [Test worker] DEBUG io.mockk.proxy.jvm.StaticProxyMaker -- Transforming class android.util.Log for static method interception
23:46:07.645 [Test worker @kotlinx.coroutines.test runner#121] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for CarritoRepository name=#34
23:46:07.651 [Test worker @kotlinx.coroutines.test runner#121] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #34#35
23:46:07.659 [Test worker @kotlinx.coroutines.test runner#121] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #34#36
23:46:07.660 [Test worker @coroutine#126] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering Carrito(id=null, usuarioId=null, estado=null, items=[CarritoItem(id=ITEM1, producto=Producto(id=P1, codigo=null, nombre=Producto Test, descripcion=Descripción test, precio=10000.0, precioConDescuentoBackend=null, imagenUrl=test.jpg, categoria=CONSOLA, categoriaNombre=null, subcategoria=MANDOS, subcategoriaNombre=null, rating=4.5, ratingPromedioBackend=null, disponible=true, destacado=false, enOferta=false, stock=10, imagenesUrls=[test.jpg], fabricante=null, distribuidor=null, descuento=null, reviews=[], productosRelacionados=[]), cantidad=1)], totalServidor=null) on CarritoRepository(#34).getCarrito(continuation {})
23:46:07.665 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Disposing static mockk for class android.util.Log

Expected :true
Actual   :false
<Click to see difference>

org.opentest4j.AssertionFailedError: expected: <true> but was: <false>
	at org.junit.jupiter.api.AssertionFailureBuilder.build(AssertionFailureBuilder.java:151)
	at org.junit.jupiter.api.AssertionFailureBuilder.buildAndThrow(AssertionFailureBuilder.java:132)
	at org.junit.jupiter.api.AssertTrue.failNotTrue(AssertTrue.java:63)
	at org.junit.jupiter.api.AssertTrue.assertTrue(AssertTrue.java:36)
	at org.junit.jupiter.api.AssertTrue.assertTrue(AssertTrue.java:31)
	at org.junit.jupiter.api.Assertions.assertTrue(Assertions.java:183)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onEliminar elimina item y deja carrito vacio$1.invokeSuspend(CarritoViewModelTest.kt:209)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onEliminar elimina item y deja carrito vacio$1.invoke(CarritoViewModelTest.kt)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onEliminar elimina item y deja carrito vacio$1.invoke(CarritoViewModelTest.kt)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$1.invokeSuspend(TestBuilders.kt:316)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.test.TestDispatcher.processEvent$kotlinx_coroutines_test(TestDispatcher.kt:24)
	at kotlinx.coroutines.test.TestCoroutineScheduler.tryRunNextTaskUnless$kotlinx_coroutines_test(TestCoroutineScheduler.kt:99)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$workRunner$1.invokeSuspend(TestBuilders.kt:322)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:277)
	at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:95)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:69)
	at kotlinx.coroutines.BuildersKt.runBlocking(Unknown Source)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default(Builders.kt:48)
	at kotlinx.coroutines.BuildersKt.runBlocking$default(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersJvmKt.createTestResult(TestBuildersJvm.kt:10)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:310)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:168)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0$default(TestBuilders.kt:160)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0$default(Unknown Source)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest.onEliminar elimina item y deja carrito vacio(CarritoViewModelTest.kt:179)
	at java.base/java.lang.reflect.Method.invoke(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)

23:46:07.702 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Creating static mockk for Log
23:46:07.703 [Test worker] DEBUG io.mockk.proxy.jvm.StaticProxyMaker -- Transforming class android.util.Log for static method interception
23:46:07.781 [Test worker @kotlinx.coroutines.test runner#131] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for CarritoRepository name=#37
23:46:07.786 [Test worker @kotlinx.coroutines.test runner#131] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #37#38
23:46:07.793 [Test worker @kotlinx.coroutines.test runner#131] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #37#39
23:46:07.795 [Test worker @coroutine#137] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering Carrito(id=null, usuarioId=null, estado=null, items=[], totalServidor=null) on CarritoRepository(#37).getCarrito(continuation {})
23:46:07.830 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Disposing static mockk for class android.util.Log

Verification failed: call 1 of 1: CarritoRepository(#37).actualizarCantidad(eq(ITEM1), eq(1), any())) was not called.

Calls to same mock:
1) CarritoRepository(#37).getCarrito(continuation {})


Stack traces:
1)                                                                                    io.mockk.impl.InternalPlatform.captureStackTrace                                     (InternalPlatform.kt:125)                          
                                                                                        io.mockk.impl.stub.MockKStub.handleInvocation                                      (MockKStub.kt:254)                                 
                                                      io.mockk.impl.instantiation.JvmMockFactoryHelper$mockHandler$1.invocation                                            (JvmMockFactoryHelper.kt:24)                       
                                                                               io.mockk.proxy.jvm.advice.Interceptor.call                                                  (Interceptor.kt:21)                                
                                                                                io.mockk.proxy.jvm.advice.BaseAdvice.handle                                                (BaseAdvice.kt:42)                                 
                                                              io.mockk.proxy.jvm.advice.jvm.JvmMockKProxyInterceptor.interceptNoSuper                                      (JvmMockKProxyInterceptor.java:45)                 
                                               com.example.levelupprueba.data.repository.CarritoRepository$Subclass0.getCarrito                                            (-:-1)                                             
                                                              com.example.levelupprueba.viewmodel.CarritoViewModel$1.invokeSuspend                                         (CarritoViewModel.kt:52)                           
                                                                 kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith                                            (ContinuationImpl.kt:33)                           
                                                                                   kotlinx.coroutines.DispatchedTask.run                                                   (DispatchedTask.kt:104)                            
                                                                                kotlinx.coroutines.EventLoopImplBase.processNextEvent                                      (EventLoop.common.kt:277)                          
                                                                                kotlinx.coroutines.BlockingCoroutine.joinBlocking                                          (Builders.kt:95)                                   
                                                                           kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking                                           (Builders.kt:69)                                   
                                                                                       kotlinx.coroutines.BuildersKt.runBlocking                                           (-:1)                                              
                                                                           kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default                                   (Builders.kt:48)                                   
                                                                                       kotlinx.coroutines.BuildersKt.runBlocking$default                                   (-:1)                                              
                                                                com.example.levelupprueba.viewmodel.CarritoViewModel.<init>                                                (CarritoViewModel.kt:48)                           
    com.example.levelupprueba.viewmodel.CarritoViewModelTest$onIncrement usa actualizarCantidad con delta positivo$1.invokeSuspend                                         (CarritoViewModelTest.kt:147)                      
    com.example.levelupprueba.viewmodel.CarritoViewModelTest$onIncrement usa actualizarCantidad con delta positivo$1.invoke                                                (CarritoViewModelTest.kt:-1)                       
    com.example.levelupprueba.viewmodel.CarritoViewModelTest$onIncrement usa actualizarCantidad con delta positivo$1.invoke                                                (CarritoViewModelTest.kt:-1)                       
                                                kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$1.invokeSuspend                                         (TestBuilders.kt:316)                              
                                                                 kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith                                            (ContinuationImpl.kt:33)                           
                                                                                   kotlinx.coroutines.DispatchedTask.run                                                   (DispatchedTask.kt:104)                            
                                                                              kotlinx.coroutines.test.TestDispatcher.processEvent$kotlinx_coroutines_test                  (TestDispatcher.kt:24)                             
                                                                      kotlinx.coroutines.test.TestCoroutineScheduler.tryRunNextTaskUnless$kotlinx_coroutines_test          (TestCoroutineScheduler.kt:99)                     
                                     kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$workRunner$1.invokeSuspend                                         (TestBuilders.kt:322)                              
                                                                 kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith                                            (ContinuationImpl.kt:33)                           
                                                                                   kotlinx.coroutines.DispatchedTask.run                                                   (DispatchedTask.kt:104)                            
                                                                                kotlinx.coroutines.EventLoopImplBase.processNextEvent                                      (EventLoop.common.kt:277)                          
                                                                                kotlinx.coroutines.BlockingCoroutine.joinBlocking                                          (Builders.kt:95)                                   
                                                                           kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking                                           (Builders.kt:69)                                   
                                                                                       kotlinx.coroutines.BuildersKt.runBlocking                                           (-:1)                                              
                                                                           kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default                                   (Builders.kt:48)                                   
                                                                                       kotlinx.coroutines.BuildersKt.runBlocking$default                                   (-:1)                                              
                                                                           kotlinx.coroutines.test.TestBuildersJvmKt.createTestResult                                      (TestBuildersJvm.kt:10)                            
                                                              kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0                                       (TestBuilders.kt:310)                              
                                                                              kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0                                       (-:1)                                              
                                                              kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0                                       (TestBuilders.kt:168)                              
                                                                              kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0                                       (-:1)                                              
                                                              kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0$default                               (TestBuilders.kt:160)                              
                                                                              kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0$default                               (-:1)                                              
                                                            com.example.levelupprueba.viewmodel.CarritoViewModelTest.onIncrement usa actualizarCantidad con delta positivo (CarritoViewModelTest.kt:129)                      
                                                                     jdk.internal.reflect.DirectMethodHandleAccessor.invoke                                                (-:-1)                                             
                                                                                            java.lang.reflect.Method.invoke                                                (-:-1)                                             
                                                                     org.junit.platform.commons.util.ReflectionUtils.invokeMethod                                          (ReflectionUtils.java:728)                         
                                                                 org.junit.jupiter.engine.execution.MethodInvocation.proceed                                               (MethodInvocation.java:60)                         
                                  org.junit.jupiter.engine.execution.InvocationInterceptorChain$ValidatingInvocation.proceed                                               (InvocationInterceptorChain.java:131)              
                                                                 org.junit.jupiter.engine.extension.TimeoutExtension.intercept                                             (TimeoutExtension.java:156)                        
                                                                 org.junit.jupiter.engine.extension.TimeoutExtension.interceptTestableMethod                               (TimeoutExtension.java:147)                        
                                                                 org.junit.jupiter.engine.extension.TimeoutExtension.interceptTestMethod                                   (TimeoutExtension.java:86)                         
                          org.junit.jupiter.engine.execution.InterceptingExecutableInvoker$ReflectiveInterceptorCall.lambda$ofVoidMethod$0                                 (InterceptingExecutableInvoker.java:103)           
                                                    org.junit.jupiter.engine.execution.InterceptingExecutableInvoker.lambda$invoke$0                                       (InterceptingExecutableInvoker.java:93)            
                                 org.junit.jupiter.engine.execution.InvocationInterceptorChain$InterceptedInvocation.proceed                                               (InvocationInterceptorChain.java:106)              
                                                       org.junit.jupiter.engine.execution.InvocationInterceptorChain.proceed                                               (InvocationInterceptorChain.java:64)               
                                                       org.junit.jupiter.engine.execution.InvocationInterceptorChain.chainAndInvoke                                        (InvocationInterceptorChain.java:45)               
                                                       org.junit.jupiter.engine.execution.InvocationInterceptorChain.invoke                                                (InvocationInterceptorChain.java:37)               
                                                    org.junit.jupiter.engine.execution.InterceptingExecutableInvoker.invoke                                                (InterceptingExecutableInvoker.java:92)            
                                                    org.junit.jupiter.engine.execution.InterceptingExecutableInvoker.invoke                                                (InterceptingExecutableInvoker.java:86)            
                                                        org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.lambda$invokeTestMethod$7                             (TestMethodTestDescriptor.java:218)                
                                                   org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute                                               (ThrowableCollector.java:73)                       
                                                        org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.invokeTestMethod                                      (TestMethodTestDescriptor.java:214)                
                                                        org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute                                               (TestMethodTestDescriptor.java:139)                
                                                        org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute                                               (TestMethodTestDescriptor.java:69)                 
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$6                           (NodeTestTask.java:151)                            
                                                   org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute                                               (ThrowableCollector.java:73)                       
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8                           (NodeTestTask.java:141)                            
                                                                 org.junit.platform.engine.support.hierarchical.Node.around                                                (Node.java:137)                                    
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$9                           (NodeTestTask.java:139)                            
                                                   org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute                                               (ThrowableCollector.java:73)                       
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively                                    (NodeTestTask.java:138)                            
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.execute                                               (NodeTestTask.java:95)                             
                                                                                                 java.util.ArrayList.forEach                                               (-:-1)                                             
                            org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll                                             (SameThreadHierarchicalTestExecutorService.java:41)
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$6                           (NodeTestTask.java:155)                            
                                                   org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute                                               (ThrowableCollector.java:73)                       
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8                           (NodeTestTask.java:141)                            
                                                                 org.junit.platform.engine.support.hierarchical.Node.around                                                (Node.java:137)                                    
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$9                           (NodeTestTask.java:139)                            
                                                   org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute                                               (ThrowableCollector.java:73)                       
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively                                    (NodeTestTask.java:138)                            
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.execute                                               (NodeTestTask.java:95)                             
                                                                                                 java.util.ArrayList.forEach                                               (-:-1)                                             
                            org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll                                             (SameThreadHierarchicalTestExecutorService.java:41)
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$6                           (NodeTestTask.java:155)                            
                                                   org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute                                               (ThrowableCollector.java:73)                       
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8                           (NodeTestTask.java:141)                            
                                                                 org.junit.platform.engine.support.hierarchical.Node.around                                                (Node.java:137)                                    
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$9                           (NodeTestTask.java:139)                            
                                                   org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute                                               (ThrowableCollector.java:73)                       
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively                                    (NodeTestTask.java:138)                            
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.execute                                               (NodeTestTask.java:95)                             
                            org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.submit                                                (SameThreadHierarchicalTestExecutorService.java:35)
                                             org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor.execute                                               (HierarchicalTestExecutor.java:57)                 
                                               org.junit.platform.engine.support.hierarchical.HierarchicalTestEngine.execute                                               (HierarchicalTestEngine.java:54)                   
                                                        org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute                                               (EngineExecutionOrchestrator.java:198)             
                                                        org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute                                               (EngineExecutionOrchestrator.java:169)             
                                                        org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute                                               (EngineExecutionOrchestrator.java:93)              
                                                        org.junit.platform.launcher.core.EngineExecutionOrchestrator.lambda$execute$0                                      (EngineExecutionOrchestrator.java:58)              
                                                        org.junit.platform.launcher.core.EngineExecutionOrchestrator.withInterceptedStreams                                (EngineExecutionOrchestrator.java:141)             
                                                        org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute                                               (EngineExecutionOrchestrator.java:57)              
                                                                    org.junit.platform.launcher.core.DefaultLauncher.execute                                               (DefaultLauncher.java:103)                         
                                                                    org.junit.platform.launcher.core.DefaultLauncher.execute                                               (DefaultLauncher.java:85)                          
                                                                 org.junit.platform.launcher.core.DelegatingLauncher.execute                                               (DelegatingLauncher.java:47)                       
   org.gradle.api.internal.tasks.testing.junitplatform.JUnitPlatformTestClassProcessor$CollectAllTestClassesExecutor.processAllTestClasses                                 (JUnitPlatformTestClassProcessor.java:124)         
   org.gradle.api.internal.tasks.testing.junitplatform.JUnitPlatformTestClassProcessor$CollectAllTestClassesExecutor.access$000                                            (JUnitPlatformTestClassProcessor.java:99)          
                                 org.gradle.api.internal.tasks.testing.junitplatform.JUnitPlatformTestClassProcessor.stop                                                  (JUnitPlatformTestClassProcessor.java:94)          
                                                       org.gradle.api.internal.tasks.testing.SuiteTestClassProcessor.stop                                                  (SuiteTestClassProcessor.java:63)                  
                                                                     jdk.internal.reflect.DirectMethodHandleAccessor.invoke                                                (-:-1)                                             
                                                                                            java.lang.reflect.Method.invoke                                                (-:-1)                                             
                                                                     org.gradle.internal.dispatch.ReflectionDispatch.dispatch                                              (ReflectionDispatch.java:36)                       
                                                                     org.gradle.internal.dispatch.ReflectionDispatch.dispatch                                              (ReflectionDispatch.java:24)                       
                                                             org.gradle.internal.dispatch.ContextClassLoaderDispatch.dispatch                                              (ContextClassLoaderDispatch.java:33)               
                                      org.gradle.internal.dispatch.ProxyDispatchAdapter$DispatchingInvocationHandler.invoke                                                (ProxyDispatchAdapter.java:92)                     
                                                                                                  jdk.proxy2.$Proxy6.stop                                                  (-:-1)                                             
                                                           org.gradle.api.internal.tasks.testing.worker.TestWorker$3.run                                                   (TestWorker.java:200)                              
                                                             org.gradle.api.internal.tasks.testing.worker.TestWorker.executeAndMaintainThreadName                          (TestWorker.java:132)                              
                                                             org.gradle.api.internal.tasks.testing.worker.TestWorker.execute                                               (TestWorker.java:103)                              
                                                             org.gradle.api.internal.tasks.testing.worker.TestWorker.execute                                               (TestWorker.java:63)                               
                                                      org.gradle.process.internal.worker.child.ActionExecutionWorker.execute                                               (ActionExecutionWorker.java:56)                    
                                         org.gradle.process.internal.worker.child.SystemApplicationClassLoaderWorker.call                                                  (SystemApplicationClassLoaderWorker.java:121)      
                                         org.gradle.process.internal.worker.child.SystemApplicationClassLoaderWorker.call                                                  (SystemApplicationClassLoaderWorker.java:71)       
                                                          worker.org.gradle.process.internal.worker.GradleWorkerMain.run                                                   (GradleWorkerMain.java:69)                         
                                                          worker.org.gradle.process.internal.worker.GradleWorkerMain.main                                                  (GradleWorkerMain.java:74)                         
java.lang.AssertionError: Verification failed: call 1 of 1: CarritoRepository(#37).actualizarCantidad(eq(ITEM1), eq(1), any())) was not called.

Calls to same mock:
1) CarritoRepository(#37).getCarrito(continuation {})


Stack traces:
1)                                                                                    io.mockk.impl.InternalPlatform.captureStackTrace                                     (InternalPlatform.kt:125)                          
                                                                                        io.mockk.impl.stub.MockKStub.handleInvocation                                      (MockKStub.kt:254)                                 
                                                      io.mockk.impl.instantiation.JvmMockFactoryHelper$mockHandler$1.invocation                                            (JvmMockFactoryHelper.kt:24)                       
                                                                               io.mockk.proxy.jvm.advice.Interceptor.call                                                  (Interceptor.kt:21)                                
                                                                                io.mockk.proxy.jvm.advice.BaseAdvice.handle                                                (BaseAdvice.kt:42)                                 
                                                              io.mockk.proxy.jvm.advice.jvm.JvmMockKProxyInterceptor.interceptNoSuper                                      (JvmMockKProxyInterceptor.java:45)                 
                                               com.example.levelupprueba.data.repository.CarritoRepository$Subclass0.getCarrito                                            (-:-1)                                             
                                                              com.example.levelupprueba.viewmodel.CarritoViewModel$1.invokeSuspend                                         (CarritoViewModel.kt:52)                           
                                                                 kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith                                            (ContinuationImpl.kt:33)                           
                                                                                   kotlinx.coroutines.DispatchedTask.run                                                   (DispatchedTask.kt:104)                            
                                                                                kotlinx.coroutines.EventLoopImplBase.processNextEvent                                      (EventLoop.common.kt:277)                          
                                                                                kotlinx.coroutines.BlockingCoroutine.joinBlocking                                          (Builders.kt:95)                                   
                                                                           kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking                                           (Builders.kt:69)                                   
                                                                                       kotlinx.coroutines.BuildersKt.runBlocking                                           (-:1)                                              
                                                                           kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default                                   (Builders.kt:48)                                   
                                                                                       kotlinx.coroutines.BuildersKt.runBlocking$default                                   (-:1)                                              
                                                                com.example.levelupprueba.viewmodel.CarritoViewModel.<init>                                                (CarritoViewModel.kt:48)                           
    com.example.levelupprueba.viewmodel.CarritoViewModelTest$onIncrement usa actualizarCantidad con delta positivo$1.invokeSuspend                                         (CarritoViewModelTest.kt:147)                      
    com.example.levelupprueba.viewmodel.CarritoViewModelTest$onIncrement usa actualizarCantidad con delta positivo$1.invoke                                                (CarritoViewModelTest.kt:-1)                       
    com.example.levelupprueba.viewmodel.CarritoViewModelTest$onIncrement usa actualizarCantidad con delta positivo$1.invoke                                                (CarritoViewModelTest.kt:-1)                       
                                                kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$1.invokeSuspend                                         (TestBuilders.kt:316)                              
                                                                 kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith                                            (ContinuationImpl.kt:33)                           
                                                                                   kotlinx.coroutines.DispatchedTask.run                                                   (DispatchedTask.kt:104)                            
                                                                              kotlinx.coroutines.test.TestDispatcher.processEvent$kotlinx_coroutines_test                  (TestDispatcher.kt:24)                             
                                                                      kotlinx.coroutines.test.TestCoroutineScheduler.tryRunNextTaskUnless$kotlinx_coroutines_test          (TestCoroutineScheduler.kt:99)                     
                                     kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$workRunner$1.invokeSuspend                                         (TestBuilders.kt:322)                              
                                                                 kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith                                            (ContinuationImpl.kt:33)                           
                                                                                   kotlinx.coroutines.DispatchedTask.run                                                   (DispatchedTask.kt:104)                            
                                                                                kotlinx.coroutines.EventLoopImplBase.processNextEvent                                      (EventLoop.common.kt:277)                          
                                                                                kotlinx.coroutines.BlockingCoroutine.joinBlocking                                          (Builders.kt:95)                                   
                                                                           kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking                                           (Builders.kt:69)                                   
                                                                                       kotlinx.coroutines.BuildersKt.runBlocking                                           (-:1)                                              
                                                                           kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default                                   (Builders.kt:48)                                   
                                                                                       kotlinx.coroutines.BuildersKt.runBlocking$default                                   (-:1)                                              
                                                                           kotlinx.coroutines.test.TestBuildersJvmKt.createTestResult                                      (TestBuildersJvm.kt:10)                            
                                                              kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0                                       (TestBuilders.kt:310)                              
                                                                              kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0                                       (-:1)                                              
                                                              kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0                                       (TestBuilders.kt:168)                              
                                                                              kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0                                       (-:1)                                              
                                                              kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0$default                               (TestBuilders.kt:160)                              
                                                                              kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0$default                               (-:1)                                              
                                                            com.example.levelupprueba.viewmodel.CarritoViewModelTest.onIncrement usa actualizarCantidad con delta positivo (CarritoViewModelTest.kt:129)                      
                                                                     jdk.internal.reflect.DirectMethodHandleAccessor.invoke                                                (-:-1)                                             
                                                                                            java.lang.reflect.Method.invoke                                                (-:-1)                                             
                                                                     org.junit.platform.commons.util.ReflectionUtils.invokeMethod                                          (ReflectionUtils.java:728)                         
                                                                 org.junit.jupiter.engine.execution.MethodInvocation.proceed                                               (MethodInvocation.java:60)                         
                                  org.junit.jupiter.engine.execution.InvocationInterceptorChain$ValidatingInvocation.proceed                                               (InvocationInterceptorChain.java:131)              
                                                                 org.junit.jupiter.engine.extension.TimeoutExtension.intercept                                             (TimeoutExtension.java:156)                        
                                                                 org.junit.jupiter.engine.extension.TimeoutExtension.interceptTestableMethod                               (TimeoutExtension.java:147)                        
                                                                 org.junit.jupiter.engine.extension.TimeoutExtension.interceptTestMethod                                   (TimeoutExtension.java:86)                         
                          org.junit.jupiter.engine.execution.InterceptingExecutableInvoker$ReflectiveInterceptorCall.lambda$ofVoidMethod$0                                 (InterceptingExecutableInvoker.java:103)           
                                                    org.junit.jupiter.engine.execution.InterceptingExecutableInvoker.lambda$invoke$0                                       (InterceptingExecutableInvoker.java:93)            
                                 org.junit.jupiter.engine.execution.InvocationInterceptorChain$InterceptedInvocation.proceed                                               (InvocationInterceptorChain.java:106)              
                                                       org.junit.jupiter.engine.execution.InvocationInterceptorChain.proceed                                               (InvocationInterceptorChain.java:64)               
                                                       org.junit.jupiter.engine.execution.InvocationInterceptorChain.chainAndInvoke                                        (InvocationInterceptorChain.java:45)               
                                                       org.junit.jupiter.engine.execution.InvocationInterceptorChain.invoke                                                (InvocationInterceptorChain.java:37)               
                                                    org.junit.jupiter.engine.execution.InterceptingExecutableInvoker.invoke                                                (InterceptingExecutableInvoker.java:92)            
                                                    org.junit.jupiter.engine.execution.InterceptingExecutableInvoker.invoke                                                (InterceptingExecutableInvoker.java:86)            
                                                        org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.lambda$invokeTestMethod$7                             (TestMethodTestDescriptor.java:218)                
                                                   org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute                                               (ThrowableCollector.java:73)                       
                                                        org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.invokeTestMethod                                      (TestMethodTestDescriptor.java:214)                
                                                        org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute                                               (TestMethodTestDescriptor.java:139)                
                                                        org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute                                               (TestMethodTestDescriptor.java:69)                 
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$6                           (NodeTestTask.java:151)                            
                                                   org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute                                               (ThrowableCollector.java:73)                       
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8                           (NodeTestTask.java:141)                            
                                                                 org.junit.platform.engine.support.hierarchical.Node.around                                                (Node.java:137)                                    
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$9                           (NodeTestTask.java:139)                            
                                                   org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute                                               (ThrowableCollector.java:73)                       
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively                                    (NodeTestTask.java:138)                            
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.execute                                               (NodeTestTask.java:95)                             
                                                                                                 java.util.ArrayList.forEach                                               (-:-1)                                             
                            org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll                                             (SameThreadHierarchicalTestExecutorService.java:41)
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$6                           (NodeTestTask.java:155)                            
                                                   org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute                                               (ThrowableCollector.java:73)                       
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8                           (NodeTestTask.java:141)                            
                                                                 org.junit.platform.engine.support.hierarchical.Node.around                                                (Node.java:137)                                    
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$9                           (NodeTestTask.java:139)                            
                                                   org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute                                               (ThrowableCollector.java:73)                       
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively                                    (NodeTestTask.java:138)                            
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.execute                                               (NodeTestTask.java:95)                             
                                                                                                 java.util.ArrayList.forEach                                               (-:-1)                                             
                            org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll                                             (SameThreadHierarchicalTestExecutorService.java:41)
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$6                           (NodeTestTask.java:155)                            
                                                   org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute                                               (ThrowableCollector.java:73)                       
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8                           (NodeTestTask.java:141)                            
                                                                 org.junit.platform.engine.support.hierarchical.Node.around                                                (Node.java:137)                                    
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$9                           (NodeTestTask.java:139)                            
                                                   org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute                                               (ThrowableCollector.java:73)                       
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively                                    (NodeTestTask.java:138)                            
                                                         org.junit.platform.engine.support.hierarchical.NodeTestTask.execute                                               (NodeTestTask.java:95)                             
                            org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.submit                                                (SameThreadHierarchicalTestExecutorService.java:35)
                                             org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor.execute                                               (HierarchicalTestExecutor.java:57)                 
                                               org.junit.platform.engine.support.hierarchical.HierarchicalTestEngine.execute                                               (HierarchicalTestEngine.java:54)                   
                                                        org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute                                               (EngineExecutionOrchestrator.java:198)             
                                                        org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute                                               (EngineExecutionOrchestrator.java:169)             
                                                        org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute                                               (EngineExecutionOrchestrator.java:93)              
                                                        org.junit.platform.launcher.core.EngineExecutionOrchestrator.lambda$execute$0                                      (EngineExecutionOrchestrator.java:58)              
                                                        org.junit.platform.launcher.core.EngineExecutionOrchestrator.withInterceptedStreams                                (EngineExecutionOrchestrator.java:141)             
                                                        org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute                                               (EngineExecutionOrchestrator.java:57)              
                                                                    org.junit.platform.launcher.core.DefaultLauncher.execute                                               (DefaultLauncher.java:103)                         
                                                                    org.junit.platform.launcher.core.DefaultLauncher.execute                                               (DefaultLauncher.java:85)                          
                                                                 org.junit.platform.launcher.core.DelegatingLauncher.execute                                               (DelegatingLauncher.java:47)                       
   org.gradle.api.internal.tasks.testing.junitplatform.JUnitPlatformTestClassProcessor$CollectAllTestClassesExecutor.processAllTestClasses                                 (JUnitPlatformTestClassProcessor.java:124)         
   org.gradle.api.internal.tasks.testing.junitplatform.JUnitPlatformTestClassProcessor$CollectAllTestClassesExecutor.access$000                                            (JUnitPlatformTestClassProcessor.java:99)          
                                 org.gradle.api.internal.tasks.testing.junitplatform.JUnitPlatformTestClassProcessor.stop                                                  (JUnitPlatformTestClassProcessor.java:94)          
                                                       org.gradle.api.internal.tasks.testing.SuiteTestClassProcessor.stop                                                  (SuiteTestClassProcessor.java:63)                  
                                                                     jdk.internal.reflect.DirectMethodHandleAccessor.invoke                                                (-:-1)                                             
                                                                                            java.lang.reflect.Method.invoke                                                (-:-1)                                             
                                                                     org.gradle.internal.dispatch.ReflectionDispatch.dispatch                                              (ReflectionDispatch.java:36)                       
                                                                     org.gradle.internal.dispatch.ReflectionDispatch.dispatch                                              (ReflectionDispatch.java:24)                       
                                                             org.gradle.internal.dispatch.ContextClassLoaderDispatch.dispatch                                              (ContextClassLoaderDispatch.java:33)               
                                      org.gradle.internal.dispatch.ProxyDispatchAdapter$DispatchingInvocationHandler.invoke                                                (ProxyDispatchAdapter.java:92)                     
                                                                                                  jdk.proxy2.$Proxy6.stop                                                  (-:-1)                                             
                                                           org.gradle.api.internal.tasks.testing.worker.TestWorker$3.run                                                   (TestWorker.java:200)                              
                                                             org.gradle.api.internal.tasks.testing.worker.TestWorker.executeAndMaintainThreadName                          (TestWorker.java:132)                              
                                                             org.gradle.api.internal.tasks.testing.worker.TestWorker.execute                                               (TestWorker.java:103)                              
                                                             org.gradle.api.internal.tasks.testing.worker.TestWorker.execute                                               (TestWorker.java:63)                               
                                                      org.gradle.process.internal.worker.child.ActionExecutionWorker.execute                                               (ActionExecutionWorker.java:56)                    
                                         org.gradle.process.internal.worker.child.SystemApplicationClassLoaderWorker.call                                                  (SystemApplicationClassLoaderWorker.java:121)      
                                         org.gradle.process.internal.worker.child.SystemApplicationClassLoaderWorker.call                                                  (SystemApplicationClassLoaderWorker.java:71)       
                                                          worker.org.gradle.process.internal.worker.GradleWorkerMain.run                                                   (GradleWorkerMain.java:69)                         
                                                          worker.org.gradle.process.internal.worker.GradleWorkerMain.main                                                  (GradleWorkerMain.java:74)                         
	at io.mockk.impl.recording.states.VerifyingState.failIfNotPassed(VerifyingState.kt:63)
	at io.mockk.impl.recording.states.VerifyingState.recordingDone(VerifyingState.kt:42)
	at io.mockk.impl.recording.CommonCallRecorder.done(CommonCallRecorder.kt:47)
	at io.mockk.impl.eval.RecordedBlockEvaluator.record(RecordedBlockEvaluator.kt:64)
	at io.mockk.impl.eval.VerifyBlockEvaluator.verify(VerifyBlockEvaluator.kt:30)
	at io.mockk.MockKDsl.internalCoVerify(API.kt:145)
	at io.mockk.MockKKt.coVerify(MockK.kt:244)
	at io.mockk.MockKKt.coVerify$default(MockK.kt:235)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onIncrement usa actualizarCantidad con delta positivo$1.invokeSuspend(CarritoViewModelTest.kt:154)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onIncrement usa actualizarCantidad con delta positivo$1.invoke(CarritoViewModelTest.kt)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onIncrement usa actualizarCantidad con delta positivo$1.invoke(CarritoViewModelTest.kt)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$1.invokeSuspend(TestBuilders.kt:316)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.test.TestDispatcher.processEvent$kotlinx_coroutines_test(TestDispatcher.kt:24)
	at kotlinx.coroutines.test.TestCoroutineScheduler.tryRunNextTaskUnless$kotlinx_coroutines_test(TestCoroutineScheduler.kt:99)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$workRunner$1.invokeSuspend(TestBuilders.kt:322)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:277)
	at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:95)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:69)
	at kotlinx.coroutines.BuildersKt.runBlocking(Unknown Source)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default(Builders.kt:48)
	at kotlinx.coroutines.BuildersKt.runBlocking$default(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersJvmKt.createTestResult(TestBuildersJvm.kt:10)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:310)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:168)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0$default(TestBuilders.kt:160)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0$default(Unknown Source)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest.onIncrement usa actualizarCantidad con delta positivo(CarritoViewModelTest.kt:129)
	at java.base/java.lang.reflect.Method.invoke(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)

23:46:07.870 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Creating static mockk for Log
23:46:07.870 [Test worker] DEBUG io.mockk.proxy.jvm.StaticProxyMaker -- Transforming class android.util.Log for static method interception
23:46:07.938 [Test worker @kotlinx.coroutines.test runner#145] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for CarritoRepository name=#40
23:46:07.943 [Test worker @kotlinx.coroutines.test runner#145] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #40#41
23:46:07.950 [Test worker @kotlinx.coroutines.test runner#145] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #40#42
23:46:07.951 [Test worker @coroutine#151] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering Carrito(id=null, usuarioId=null, estado=null, items=[CarritoItem(id=ITEM1, producto=Producto(id=P1, codigo=null, nombre=Producto Test, descripcion=Descripción test, precio=10000.0, precioConDescuentoBackend=null, imagenUrl=test.jpg, categoria=CONSOLA, categoriaNombre=null, subcategoria=MANDOS, subcategoriaNombre=null, rating=4.5, ratingPromedioBackend=null, disponible=true, destacado=false, enOferta=false, stock=10, imagenesUrls=[test.jpg], fabricante=null, distribuidor=null, descuento=null, reviews=[], productosRelacionados=[]), cantidad=2)], totalServidor=null) on CarritoRepository(#40).getCarrito(continuation {})
23:46:07.957 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Disposing static mockk for class android.util.Log

Expected :1
Actual   :2
<Click to see difference>

org.opentest4j.AssertionFailedError: expected: <1> but was: <2>
	at org.junit.jupiter.api.AssertionFailureBuilder.build(AssertionFailureBuilder.java:151)
	at org.junit.jupiter.api.AssertionFailureBuilder.buildAndThrow(AssertionFailureBuilder.java:132)
	at org.junit.jupiter.api.AssertEquals.failNotEqual(AssertEquals.java:197)
	at org.junit.jupiter.api.AssertEquals.assertEquals(AssertEquals.java:150)
	at org.junit.jupiter.api.AssertEquals.assertEquals(AssertEquals.java:145)
	at org.junit.jupiter.api.Assertions.assertEquals(Assertions.java:531)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onDecrement usa actualizarCantidad con delta negativo y reduce cantidad$1.invokeSuspend(CarritoViewModelTest.kt:250)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onDecrement usa actualizarCantidad con delta negativo y reduce cantidad$1.invoke(CarritoViewModelTest.kt)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onDecrement usa actualizarCantidad con delta negativo y reduce cantidad$1.invoke(CarritoViewModelTest.kt)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$1.invokeSuspend(TestBuilders.kt:316)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.test.TestDispatcher.processEvent$kotlinx_coroutines_test(TestDispatcher.kt:24)
	at kotlinx.coroutines.test.TestCoroutineScheduler.tryRunNextTaskUnless$kotlinx_coroutines_test(TestCoroutineScheduler.kt:99)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$workRunner$1.invokeSuspend(TestBuilders.kt:322)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:277)
	at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:95)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:69)
	at kotlinx.coroutines.BuildersKt.runBlocking(Unknown Source)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default(Builders.kt:48)
	at kotlinx.coroutines.BuildersKt.runBlocking$default(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersJvmKt.createTestResult(TestBuildersJvm.kt:10)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:310)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:168)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0$default(TestBuilders.kt:160)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0$default(Unknown Source)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest.onDecrement usa actualizarCantidad con delta negativo y reduce cantidad(CarritoViewModelTest.kt:216)
	at java.base/java.lang.reflect.Method.invoke(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)

23:46:08.004 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Creating static mockk for Log
23:46:08.004 [Test worker] DEBUG io.mockk.proxy.jvm.StaticProxyMaker -- Transforming class android.util.Log for static method interception
23:46:08.067 [Test worker @kotlinx.coroutines.test runner#156] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for CarritoRepository name=#43
23:46:08.072 [Test worker @kotlinx.coroutines.test runner#156] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #43#44
23:46:08.078 [Test worker @kotlinx.coroutines.test runner#156] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #43#45
23:46:08.080 [Test worker @coroutine#162] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering Carrito(id=null, usuarioId=null, estado=null, items=[CarritoItem(id=ITEM1, producto=Producto(id=P1, codigo=null, nombre=Producto Test, descripcion=Descripción test, precio=10000.0, precioConDescuentoBackend=null, imagenUrl=test.jpg, categoria=CONSOLA, categoriaNombre=null, subcategoria=MANDOS, subcategoriaNombre=null, rating=4.5, ratingPromedioBackend=null, disponible=true, destacado=false, enOferta=false, stock=10, imagenesUrls=[test.jpg], fabricante=null, distribuidor=null, descuento=null, reviews=[], productosRelacionados=[]), cantidad=1)], totalServidor=null) on CarritoRepository(#43).getCarrito(continuation {})
23:46:08.085 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Disposing static mockk for class android.util.Log

Expected :2
Actual   :1
<Click to see difference>

org.opentest4j.AssertionFailedError: expected: <2> but was: <1>
	at org.junit.jupiter.api.AssertionFailureBuilder.build(AssertionFailureBuilder.java:151)
	at org.junit.jupiter.api.AssertionFailureBuilder.buildAndThrow(AssertionFailureBuilder.java:132)
	at org.junit.jupiter.api.AssertEquals.failNotEqual(AssertEquals.java:197)
	at org.junit.jupiter.api.AssertEquals.assertEquals(AssertEquals.java:150)
	at org.junit.jupiter.api.AssertEquals.assertEquals(AssertEquals.java:145)
	at org.junit.jupiter.api.Assertions.assertEquals(Assertions.java:531)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onIncrement sube cantidad y actualiza estado a 2$1.invokeSuspend(CarritoViewModelTest.kt:404)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onIncrement sube cantidad y actualiza estado a 2$1.invoke(CarritoViewModelTest.kt)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onIncrement sube cantidad y actualiza estado a 2$1.invoke(CarritoViewModelTest.kt)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$1.invokeSuspend(TestBuilders.kt:316)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.test.TestDispatcher.processEvent$kotlinx_coroutines_test(TestDispatcher.kt:24)
	at kotlinx.coroutines.test.TestCoroutineScheduler.tryRunNextTaskUnless$kotlinx_coroutines_test(TestCoroutineScheduler.kt:99)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$workRunner$1.invokeSuspend(TestBuilders.kt:322)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:277)
	at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:95)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:69)
	at kotlinx.coroutines.BuildersKt.runBlocking(Unknown Source)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default(Builders.kt:48)
	at kotlinx.coroutines.BuildersKt.runBlocking$default(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersJvmKt.createTestResult(TestBuildersJvm.kt:10)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:310)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:168)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0$default(TestBuilders.kt:160)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0$default(Unknown Source)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest.onIncrement sube cantidad y actualiza estado a 2(CarritoViewModelTest.kt:376)
	at java.base/java.lang.reflect.Method.invoke(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)

23:46:08.134 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Creating static mockk for Log
23:46:08.135 [Test worker] DEBUG io.mockk.proxy.jvm.StaticProxyMaker -- Transforming class android.util.Log for static method interception
23:46:08.214 [Test worker @kotlinx.coroutines.test runner#167] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for CarritoRepository name=#46
23:46:08.219 [Test worker @kotlinx.coroutines.test runner#167] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #46#47
23:46:08.226 [Test worker @kotlinx.coroutines.test runner#167] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #46#48
23:46:08.230 [Test worker @coroutine#173] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering Carrito(id=null, usuarioId=null, estado=null, items=[], totalServidor=null) on CarritoRepository(#46).getCarrito(continuation {})
23:46:08.233 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Disposing static mockk for class android.util.Log

Expected :Carrito(id=null, usuarioId=null, estado=null, items=[CarritoItem(id=I1, producto=Producto(id=P1, codigo=null, nombre=Producto Test, descripcion=Descripción test, precio=10000.0, precioConDescuentoBackend=null, imagenUrl=test.jpg, categoria=CONSOLA, c ...

Actual   :Carrito(id=null, usuarioId=null, estado=null, items=[], totalServidor=null)
<Click to see difference>

org.opentest4j.AssertionFailedError: expected: <Carrito(id=null, usuarioId=null, estado=null, items=[CarritoItem(id=I1, producto=Producto(id=P1, codigo=null, nombre=Producto Test, descripcion=Descripción test, precio=10000.0, precioConDescuentoBackend=null, imagenUrl=test.jpg, categoria=CONSOLA, categoriaNombre=null, subcategoria=MANDOS, subcategoriaNombre=null, rating=4.5, ratingPromedioBackend=null, disponible=true, destacado=false, enOferta=false, stock=10, imagenesUrls=[test.jpg], fabricante=null, distribuidor=null, descuento=null, reviews=[], productosRelacionados=[]), cantidad=1)], totalServidor=null)> but was: <Carrito(id=null, usuarioId=null, estado=null, items=[], totalServidor=null)>
	at app//org.junit.jupiter.api.AssertionFailureBuilder.build(AssertionFailureBuilder.java:151)
	at app//org.junit.jupiter.api.AssertionFailureBuilder.buildAndThrow(AssertionFailureBuilder.java:132)
	at app//org.junit.jupiter.api.AssertEquals.failNotEqual(AssertEquals.java:197)
	at app//org.junit.jupiter.api.AssertEquals.assertEquals(AssertEquals.java:182)
	at app//org.junit.jupiter.api.AssertEquals.assertEquals(AssertEquals.java:177)
	at app//org.junit.jupiter.api.Assertions.assertEquals(Assertions.java:1145)
	at app//com.example.levelupprueba.viewmodel.CarritoViewModelTest$onAgregar actualiza carrito en exito (loading queda false)$1.invokeSuspend(CarritoViewModelTest.kt:645)
	at app//com.example.levelupprueba.viewmodel.CarritoViewModelTest$onAgregar actualiza carrito en exito (loading queda false)$1.invoke(CarritoViewModelTest.kt)
	at app//com.example.levelupprueba.viewmodel.CarritoViewModelTest$onAgregar actualiza carrito en exito (loading queda false)$1.invoke(CarritoViewModelTest.kt)
	at app//kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$1.invokeSuspend(TestBuilders.kt:316)
	at app//kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at app//kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at app//kotlinx.coroutines.test.TestDispatcher.processEvent$kotlinx_coroutines_test(TestDispatcher.kt:24)
	at app//kotlinx.coroutines.test.TestCoroutineScheduler.tryRunNextTaskUnless$kotlinx_coroutines_test(TestCoroutineScheduler.kt:99)
	at app//kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$workRunner$1.invokeSuspend(TestBuilders.kt:322)
	at app//kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at app//kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at app//kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:277)
	at app//kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:95)
	at app//kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:69)
	at app//kotlinx.coroutines.BuildersKt.runBlocking(Unknown Source)
	at app//kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default(Builders.kt:48)
	at app//kotlinx.coroutines.BuildersKt.runBlocking$default(Unknown Source)
	at app//kotlinx.coroutines.test.TestBuildersJvmKt.createTestResult(TestBuildersJvm.kt:10)
	at app//kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:310)
	at app//kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at app//kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:168)
	at app//kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at app//kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0$default(TestBuilders.kt:160)
	at app//kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0$default(Unknown Source)
	at app//com.example.levelupprueba.viewmodel.CarritoViewModelTest.onAgregar actualiza carrito en exito (loading queda false)(CarritoViewModelTest.kt:617)
	at java.base@21.0.7/java.lang.reflect.Method.invoke(Unknown Source)
	at java.base@21.0.7/java.util.ArrayList.forEach(Unknown Source)
	at java.base@21.0.7/java.util.ArrayList.forEach(Unknown Source)

23:46:08.281 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Creating static mockk for Log
23:46:08.282 [Test worker] DEBUG io.mockk.proxy.jvm.StaticProxyMaker -- Transforming class android.util.Log for static method interception
23:46:08.344 [Test worker @kotlinx.coroutines.test runner#178] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for CarritoRepository name=#49
23:46:08.348 [Test worker @kotlinx.coroutines.test runner#178] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #49#50
23:46:08.354 [Test worker @kotlinx.coroutines.test runner#178] DEBUG io.mockk.impl.instantiation.AbstractMockFactory -- Creating mockk for Carrito name=child of #49#51
23:46:08.355 [Test worker @coroutine#184] DEBUG io.mockk.impl.recording.states.AnsweringState -- Answering Carrito(id=null, usuarioId=null, estado=null, items=[CarritoItem(id=ITEM1, producto=Producto(id=P1, codigo=null, nombre=Producto Test, descripcion=Descripción test, precio=10000.0, precioConDescuentoBackend=null, imagenUrl=test.jpg, categoria=CONSOLA, categoriaNombre=null, subcategoria=MANDOS, subcategoriaNombre=null, rating=4.5, ratingPromedioBackend=null, disponible=true, destacado=false, enOferta=false, stock=10, imagenesUrls=[test.jpg], fabricante=null, distribuidor=null, descuento=null, reviews=[], productosRelacionados=[]), cantidad=1)], totalServidor=null) on CarritoRepository(#49).getCarrito(continuation {})
23:46:08.358 [Test worker] DEBUG io.mockk.impl.instantiation.JvmStaticMockFactory -- Disposing static mockk for class android.util.Log

Expected :fallo incrementar
Actual   :null
<Click to see difference>

org.opentest4j.AssertionFailedError: expected: <fallo incrementar> but was: <null>
	at org.junit.jupiter.api.AssertionFailureBuilder.build(AssertionFailureBuilder.java:151)
	at org.junit.jupiter.api.AssertionFailureBuilder.buildAndThrow(AssertionFailureBuilder.java:132)
	at org.junit.jupiter.api.AssertEquals.failNotEqual(AssertEquals.java:197)
	at org.junit.jupiter.api.AssertEquals.assertEquals(AssertEquals.java:182)
	at org.junit.jupiter.api.AssertEquals.assertEquals(AssertEquals.java:177)
	at org.junit.jupiter.api.Assertions.assertEquals(Assertions.java:1145)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onIncrement con error mantiene carrito y expone mensaje$1.invokeSuspend(CarritoViewModelTest.kt:471)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onIncrement con error mantiene carrito y expone mensaje$1.invoke(CarritoViewModelTest.kt)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest$onIncrement con error mantiene carrito y expone mensaje$1.invoke(CarritoViewModelTest.kt)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$1.invokeSuspend(TestBuilders.kt:316)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.test.TestDispatcher.processEvent$kotlinx_coroutines_test(TestDispatcher.kt:24)
	at kotlinx.coroutines.test.TestCoroutineScheduler.tryRunNextTaskUnless$kotlinx_coroutines_test(TestCoroutineScheduler.kt:99)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt$runTest$2$1$workRunner$1.invokeSuspend(TestBuilders.kt:322)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:104)
	at kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:277)
	at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:95)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:69)
	at kotlinx.coroutines.BuildersKt.runBlocking(Unknown Source)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default(Builders.kt:48)
	at kotlinx.coroutines.BuildersKt.runBlocking$default(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersJvmKt.createTestResult(TestBuildersJvm.kt:10)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:310)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0(TestBuilders.kt:168)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0(Unknown Source)
	at kotlinx.coroutines.test.TestBuildersKt__TestBuildersKt.runTest-8Mi8wO0$default(TestBuilders.kt:160)
	at kotlinx.coroutines.test.TestBuildersKt.runTest-8Mi8wO0$default(Unknown Source)
	at com.example.levelupprueba.viewmodel.CarritoViewModelTest.onIncrement con error mantiene carrito y expone mensaje(CarritoViewModelTest.kt:443)
	at java.base/java.lang.reflect.Method.invoke(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)
	at java.base/java.util.ArrayList.forEach(Unknown Source)



