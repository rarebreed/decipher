# Overview

decipher is a library to learn deep learning in kotlin, adding to or extending existing
libraries as needed.  It is also a functional programming library.  This library
will contain jupyter notebooks, tensorflow models, math extensions, and async and
concurrency tools to help make ML tasks easier.

However, the primary goal of decipher is educational.  To teach those who are new to data
science the fundamentals of math and DL, and those who are already good at data science
the best practices of software and data engineering.

To put it another, one of the main goals is to meld data science, data engineering and
software engineering into a holistic whole.

## Choice of languages

decipher is primarily to be written in the kotlin language, with the occasional script, or
library written in rust -> webassembly.  There may also be some scattered python3 code, mostly
to act as a rosetta stone for those already familiar with python, though any python code here
will be python3 with type hints.

As scala 3 matures, there may also be some scala code, especially once spark 3.x works
with scala 2.13.3 and we can compile scala 3 code that scala 2.12.3+ can use.

There may also be some kotlinjs examples too, to make use of tensorflowjs

## Build tooling

This project uses gradle, and specifically some advanced gradle with multiprojects and build
scripts that define gradle extensions.

### common-kotlin-lib 

The `common-kotlin-lib` subproject is a custom gradle plugin, that when included in the `plugins` section, will
automatically add kotlin dependencies and plugins, and some very common dependencies (eg, jackson).  This custom plugin
can also be used as a template to build other custom plugins and new tasks.

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

As much as possible, decipher will use FP concepts.  We might even talk about Functors, Applicatives and Monads as
interfaces, or Monoids and Sequences as datatypes.  But most importantly, we will introduce best practices avoiding many
common problems:

- Use the arrow library to provide better data types and interfaces
- Use Either instead of throwing exceptions
- Use Flows or Channels to create reactive-like push based data flows instead of pull based imperative models
- Design computations as pipelines rather than imperative sequences
- Use immutable data as much as possible
- Minimize functional dependencies either through DI tools like koin, or simple argument passing
- Model functions as curried functions (auto-implemented with annotation processor)

### alonzo

The `alonzo` subproject contains some helper functions, as well as an annotation processor that can autocurry a function
or class by generating new code that creates curried versions of the functions.

## Roadmap

- Finish the common-kotlin-lib custom plugin, so we can use it in our other projects
    - Publish the plugin to plugins.gradle.com
- Finish up alonzo, to generate curried functions and methods in classes
- Write up some notes in `math` in jupyter notebook
    - Practice problems in differential and integral calculus
    - Practice problems in linear algebra
    - Practice problems in statistics
- `reveles` subproject to create a neural net from scratch 
- `ignite` subproject to play with spark
- `venturi` subproject to play with airflow
- `pile` subproject to play with localstack