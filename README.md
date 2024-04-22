# Block Letters

Have you ever wanted a better way to generate letters out of letters? Likely you have in slack. Fear
not! This project solves those woes.

## Gotchas

* It seems like slack is really finicky. If you want perfect spacing, the optimal width param is 5
  or 6, provided you use an OS provided emojie.
* Monospaced characters like `8` (or w/e you like) work well
* If you use a regular emojie, but use a code block is **_also_** works just fine.

## Requirements

* Java
* Babashka

## Testing

```shell
clj -X:test
```

## Usage

### Help

```shell
bb -m core --help
```

```text
-l, --letters     The input word
-r, --replacement The value to use in place of the default
-w, --width       The character width assigned to empty space
```

### Invocation

```shell
bb -m core --letters "hello world!" --replacement "8" --width 1
```

```text
8   8  88888  8      8      88888         8   8  88888  88888  8      8888     8    
8   8  8      8      8      8   8         8   8  8   8  8   8  8      8   8    8    
88888  888    8      8      8   8         8 8 8  8   8  88888  8      8   8    8    
8   8  8      8      8      8   8         88888  8   8  88     8      8   8         
8   8  88888  88888  88888  88888         8   8  88888  8 8    88888  8888     8  
```

