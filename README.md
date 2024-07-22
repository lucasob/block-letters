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
```

```text
-l, --letters     The input word
-r, --replacement The value to use in place of the default (default ":saluting_face:")
-s, --space-char  The character that you would like to represent an empty space (default " "")
-w, --width       The character width assigned to empty space (default 11.74)
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

