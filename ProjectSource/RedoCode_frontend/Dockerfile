# build stage
FROM node:23-alpine as build-stage
WORKDIR /app
RUN yarn set version 4.0.2
COPY package*.json ./
RUN yarn 
COPY . .
RUN yarn install
RUN yarn build


# production stage
FROM nginx:stable-alpine as production-stage
COPY nginx.conf /etc/nginx/nginx.conf
RUN rm -r /etc/nginx/conf.d
COPY --from=build-stage /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]