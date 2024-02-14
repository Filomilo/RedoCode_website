FROM gcc:4.9   as build-stage
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
RUN gcc -o inputOutput inputOutput.c -static

FROM scratch
COPY --from=build-stage /usr/src/myapp/inputOutput /
CMD ["/inputOutput"]