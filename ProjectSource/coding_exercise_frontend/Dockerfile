FROM node:20
RUN npm cache clean --force
RUN yarn --version || npm install --global yarn

WORKDIR /frontend/
COPY ./package.json ./
RUN npm install
RUN yarn

COPY . .
#RUN unlink package-lock.json
#RUN yarn
ENV PORT=5173
EXPOSE 5173
#RUN yarn
CMD [ "yarn","dev","--host" ]