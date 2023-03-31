FROM nginx:1.17.2-alpine
RUN rm /etc/nginx/conf.d/default.conf
COPY load-balancer/context/nginx.conf /etc/nginx/conf.d/default.conf