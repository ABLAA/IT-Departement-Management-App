FROM node as build
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . ./
ARG REACT_APP_API_BASE_URL
ENV REACT_APP_API_BASE_URL=${REACT_APP_API_BASE_URL}
EXPOSE 3000
ENTRYPOINT ["npm","start"]


