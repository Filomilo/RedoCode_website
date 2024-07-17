# build stage
FROM cypress/included:13.13.1 as build-stage
WORKDIR /app
COPY package*.json ./
RUN yarn 
COPY . .
RUN yarn build
ENTRYPOINT ["tail", "-f", "/dev/null"]
# CMD ["tail", "-f", "/dev/null"]
# CMD ["npm", "run", "test:prod:e2e"]