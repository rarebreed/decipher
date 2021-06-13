package app.khadga.alonzo.annotations

class Example {
}

@Curry
fun foo(a: Int, b: String): Int {
    return a * 2
}