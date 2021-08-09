# Overview

decipher is a library to learn deep learning in scala3, adding to or extending existing
libraries as needed.  It is also a functional programming library.  This library
will contain jupyter notebooks, tensorflow models, math extensions, and async and
concurrency tools to help make ML tasks easier.

However, the primary goal of decipher is educational.  To teach those who are new to data
science the fundamentals of math and DL, and those who are already good at data science
the best practices of software and data engineering.

To put it another, one of the main goals is to meld data science, data engineering and
software engineering into a holistic whole.

## Choice of languages

decipher will primarily be written in the scala3 language, with the occasional script, or
library written in rust -> webassembly.  There may also be some scattered python3 code, mostly
to act as a rosetta stone for those already familiar with python, though any python code here
will be python3 with type hints.

As scala 3 matures, there may also be some scala code, especially once spark 3.x works
with scala 2.13.4 and we can compile scala 3 code that scala 2.12.3+ can use.

### Why scala3?

I had originally intended to use kotlin, but a couple of things changed my mind:

- The arrow library went through a massive change and doesn't really offer what I want now
- Kotlin dropped support for Typeclasses in [KEEP-87](https://github.com/Kotlin/KEEP/pull/87)
- Kotlin doesn't have HKT which means you can't really have Functors, Monads etc
- Kotlin doesn't have true pattern matching or destructuring
- scala3 enums, unions and intersections can better model your data than sealed classes
- Spark is written in scala and will soon offer scala 2.13 support
- Flink and kafka are also written primarily in scala
- Jetbrains seems to be focused on android/swift development for kotlin which does not interest me
- The Idea IDE is the only editor in town for kotlin
    - scala has a language server called metals so it can be used in code, emacs, vim, sublime, IDEA
- scala3 can now eliminate NPE like kotlin
- Kotlin coroutines are neat, but scala fibers in ZIO or Cats Effect 3 are purely functional
- scala3 has top level definitions (just like kotlin)
- scala3 has function extensions (just like kotlin) 
    - replaces some implicit use-cases
- scala3 doesnt need a DI framework, because it's built into the language with using/given
- scala has pure FP ecosystems like scalaz and typelevel (which I hoped arrow could have become)
- scala3's typeclass support is a better way to model behavior than OOP
    - can be done after the fact in separate source
    - less ad-hocy than function extensions, but still lets you extend behavior to classes you dont own
- scala3 has macros unlike kotlin

On the good side compared to scala3

- I think kotlin is more seamlessly integrated with java (as long as there are no coroutines)
- It's easier to pick up kotlin (but my coworkers haven't shown an interest to do so)
- Intellij IDEA integration with kotlin is top notch
- Gradle is better documented than scala's default of sbt
    - mill is a scala build tool gaining ground that is supposed to be simpler
    - Gradle can compile scala too, so this is sort of moot
- Kotlin's ecosystem is not as fragmented yet (scalaz vs tyoelevel vs cats vs zio)
- Scala3 is still very new: spark examples will be in scala2 for a long time I am sure
- Doing pure FP is not trivial (though technically not required in scala3)
- kotlin's compile times are better


So, given all the above, while kotlin is clearly superior to Java, it still leaves a lot to be desired
as well.  I was hoping to use arrow to make kotlin more Functional, but they made a recent change that
took away a pseudo-form of HKT and Typeclasses.  Sealed classes are still gross to model GADTs and 
basically require extra tagging information, and are way too verbose.

I have been slowly learning scala3 since it recently came out.  It seems to have reduced a lot of the
complexity around implicits, and broken up many implicit features into their own language syntax.  With
scala3's enums, unions, intersections, using clauses, given clauses, HKT, type lambdas and many other
new features, scala3 seems not only easier to learn than scala2, but more expressive and ergonomic.

Kotlin is trying to appease web and mobile developers by permitting both compilation to JS and native
code.  But honestly, typescript is just a better choice for js, due to much easier integration with existing JS modules and a more powerful type system.  And if I am going to write native code, it hasto be in rust (who needs a garbage collector or runtime?).  Since Jetbrains seems to be focusing a lot on 
multiplatform development, language features itself seem to have taken a backseat.

Plus, it helps that a lot of the code we will be working on is for spark applications.  Currently,
spark does not yet support scala 2.13.4+ but it soon will.  Once this happens, we can write scala3 
modules that this future spark version can use.  Since most examples you find online for spark are
either in python or scala, this is a natural fit.

While scala3 itself is not purely functional, there exists an ecosystem of libraries to make it so like
typelevel, scalaz, zio, monix, etc.  Scala, like haskell, can model true type classes like Monads, 
Functors, Applicatives, etc.

The other languages that will be used will be rust and python3.  Rust will be used for some math heavy
parts, and python3 will be used where needed for certain examples.  We will be using the java based
version of tensorflow whenever possible though.

## Build tooling

This project uses gradle, and specifically some advanced gradle with multiprojects and build
scripts that define gradle extensions.  There may also be some projects written with sbt

### common-kotlin-lib 

The `common-kotlin-lib` subproject is a custom gradle plugin, that when included in the `plugins` 
section, will automatically add kotlin dependencies and plugins, and some very common dependencies
(eg, jackson).  This custom plugin can also be used as a template to build other custom plugins and
 new tasks.

## Jupyter and Zeppelin

The nature of decipher is educational, so notebooks are a great boon with this.  Examples of
usage will be done in jupyter notebooks, with possible zeppelin support later.

We will strive to use only kotlin, but there may be some examples that use python
where it is easier to do so, or has a better teaching impact.

## Mathematics

Rudiments of calculus, linear algebra, and statistics will be covered.  These are the most 
important branches of math used in deep learning.  We will also go over a crash course in the
needed algebra and trigonometry.  The statistics portion will also go over probability theory.

We will either build, improve on or explain existing math libraries, as well as use jupyter's
graphing libraries using jupyter to help explain things.

## Deep learning frameworks

I have chosen tensorflow as the library to use because it is popular and has a large set of
algorithms already defined.  However, for pedantic purposes, we will also build our own neural
net, using existing higher dimensional algebra libraries.

We will primarily cover:

- kmath
- lets-plot
- tensorflow
- kotlindl
- keras

## Cloud, workload, and infrastructure tools

Everything today is built around the cloud.  We will try to stay clear of proprietary tooling 
 (AWS, Azure, GCP, etc) and use generic tooling like docker and kubernetes instead.  We may
even start using krustlet to build webassembly runtimes

- kubernetes: scheduler/orchestratpr for computational workloads (spark cluster runner)
- spark: To run data transformations that feed/generate data for models  
- docker: to build our images, including spark jobs
- travis: CI/CD and deployment 
- airflow: data job orchestration

## Functional Programming

As much as possible, decipher will use FP concepts.  We might even talk about Functors, Applicatives
and Monads as interfaces, or Monoids and Sequences as datatypes.  But most importantly, we will 
introduce best practices avoiding many common problems:

- Use Cats Effect instead of throwing exceptions
- Use Flows or Channels to create reactive-like push based data flows instead of pull based imperative models
- Design computations as pipelines rather than imperative sequences
- Use immutable data as much as possible
- Minimize functional dependencies either through DI tools like koin, or simple argument passing
- Model functions as curried functions (auto-implemented with annotation processor)

### alonzo

The `alonzo` subproject contains some helper functions, as well as an annotation processor that can autocurry a function
or class by generating new code that creates curried versions of the functions.

## Roadmap

- ~~Finish the common-kotlin-lib custom plugin, so we can use it in our other projects~~
    - Publish the plugin to plugins.gradle.com
- Finish up alonzo, to generate curried functions and methods in classes
- Write up some notes for `math` in jupyter notebook
    - Practice problems in differential and integral calculus
    - Practice problems in linear algebra
    - Practice problems in statistics
- `reveles` subproject to create a neural net from scratch 
- `ignite` subproject to play with spark by creating a kubernetes spark cluster
- `venturi` subproject to play with airflow by creating a kubernetes airflow cluster
- `pile` subproject to play with localstack