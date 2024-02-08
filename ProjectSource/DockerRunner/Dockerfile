FROM docker:25.0.2-dind

COPY entrypoint.sh /usr/local/bin/

# RUN dockerd --host=tcp://0.0.0.0:2376 --experimental &
EXPOSE 2376
RUN ls /usr/local/bin/
# ENTRYPOINT ["entrypoint.sh"]
ENTRYPOINT [ "/bin/sh", "/usr/local/bin/entrypoint.sh" ]
CMD []