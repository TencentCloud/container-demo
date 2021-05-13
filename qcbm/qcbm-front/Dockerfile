FROM nginx:1.19.6

# RUN rm -f /etc/nginx/nginx.conf /etc/nginx/conf.d/default.conf /usr/share/nginx/html/index.html

COPY ./webconfig/default.conf /etc/nginx/conf.d/default.conf
# COPY ./webconfig/nginx.conf /etc/nginx/nginx.conf

COPY ./build /usr/share/nginx/html
