package com.mjob.picturegallery.helpers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class TestCoroutinesRule: TestRule {
    private val testCoroutinesDispatcher =  TestCoroutineDispatcher()
    private val testCoroutineScope =  TestCoroutineScope()

    override fun apply(base: Statement, description: Description?) = object : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            Dispatchers.setMain(testCoroutinesDispatcher)

            base.evaluate()

            Dispatchers.resetMain()
            testCoroutineScope.cleanupTestCoroutines()
        }
    }

    fun runBlocking( block: suspend TestCoroutineScope.() -> Unit ){
        testCoroutineScope.runBlockingTest {
            block()
        }
    }
}