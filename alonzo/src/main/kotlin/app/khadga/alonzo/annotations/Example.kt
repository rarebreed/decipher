package app.khadga.alonzo.annotations

class Example {
}

@Curry
fun bar(a: Int, b: String): Int {
    return a * 2
}