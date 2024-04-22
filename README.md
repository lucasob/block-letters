# Block Letters

Have you ever wanted a better way to generate letters out of letters? Likely you have in slack. Fear
not! This project solves those woes.

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

