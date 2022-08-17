package sample.tutorials.sites.kotlinlang.flow.builders;

/*

   There are the following basic ways to create a flow:

   flowOf(...) functions to create a flow from a fixed set of values.

   asFlow() extension functions on various types to convert them into flows.

   flow { ... } builder function to construct arbitrary flows from sequential calls to emit function.

   channelFlow { ... } builder function to construct arbitrary flows from potentially
   concurrent calls to the send function.

   MutableStateFlow and MutableSharedFlow define the corresponding constructor
   functions to create a hot flow that can be directly updated.



   https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/

   https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-mutable-shared-flow/
   https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-mutable-state-flow/
*/
