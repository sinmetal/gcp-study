package main

import(
	"io"
	"os/exec"
	"bufio"
	"fmt"
)

func accessShell() {
	// prepare the shell
	cmd := exec.Command("//Users/shingoishimura/bin/appengine-java-sdk-1.8.9/bin/appcfg.sh", "update")

	// open the pipe

	iow, _ := cmd.StdinPipe()
	ior, _ := cmd.StdoutPipe()

	// result reader
	iors := bufio.NewReader(ior)

	// You should set up the pipe and then Start, do not Run, it will wait for return
	cmd.Start()

	// input
	io.WriteString(iow, "watermelon#fruit")
	// do not forget send the submit signal
	io.WriteString(iow, "\n")

	// result
	s, _ := iors.ReadString('\n')

	// handle result ...
	//io.WriteString(s, "\n")
	fmt.Printf("s: %q\n", s)
}
