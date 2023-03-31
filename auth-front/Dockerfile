FROM node:latest as build
WORKDIR /usr/src/app

COPY auth-front/package.json auth-front/package-lock.json ./
RUN npm install

COPY auth-front .
RUN npm run build

FROM nginx:1.17.1-alpine
COPY auth-front/nginx.conf /etc/nginx/nginx.conf
COPY --from=build /usr/src/app/dist/auth-front /usr/share/nginx/html

EXPOSE 80
