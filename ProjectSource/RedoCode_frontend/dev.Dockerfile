# build stage
FROM cypress/included:12.12.0 as build-stage
WORKDIR /app
COPY package*.json ./
RUN yarn 
COPY . .
RUN yarn build
ENTRYPOINT ["tail", "-f", "/dev/null"]
# CMD ["tail", "-f", "/dev/null"]
# CMD ["npm", "run", "test:prod:e2e"]