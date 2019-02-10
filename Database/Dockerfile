FROM postgres

ENV POSTGRES_DB rankr
EXPOSE 5432
CMD ["pg_ctl", "-D" ,"/var/lib/postgresql/data", "-l", "logfile start"]