**Tabla de Contenido**

[TOCM]

[TOC]

##Despliegue
###Ambiente Local
- Descargar Imagen del projecto de Docker Hub, [Link](https://hub.docker.com/r/dburitic/inventario-pt).

```docker
docker pull dburitic/inventario-pt:1.0.0
```
- Crear el contenedor y ejecutar la imagen, el programa al interior del contenedor usa el puerto 80, puede remplazar el puerto 8083 por el de su preferencia.

```docker
docker run -d -p 8083:80 dburitic/inventario-pt:1.0.0
```
###Kubernetes
- Desplegar el archivo de configuración **deployment.yml**.

```bash
apiVersion: apps/v1
kind: Deployment
metadata:
  name: inventario-pt-deployment
spec:
  replicas: 3
  selector:
    matchLabels:
      pod: inventario-pt-pod
  template:
    metadata:
      labels:
        app: inventario-pt-app
        pod: inventario-pt-pod
    spec:
      containers:
        - name: inventario-pt-container
          image: dburitic/inventario-pt:1.0.0
          resources:
            requests:
              memory: 300Mi
              cpu: 400m
            limits:
              memory: 300Mi
              cpu: 400m
          ports:
            - containerPort: 80
```
como se observa en la linea **18**, ya esta definida la imagen **dburitic/inventario-pt:1.0.0**  y el puerto **80**, con esta configuración se crearán por defecto 3 replicas (pods), para desplegar esté archivo se usa el siguiente comando :

```docker
kubectl apply -f deployment.yml
```
una vez desplegado esté archico puede validar el resultado con los siguientes comandos:

*Trea los deployment desplegados.*
```docker
kubectl get deployment
```

*Trea los pods.*
```docker
kubectl get pods
```

- Desplegar el archivo de configuración **service.yml**.

```docker
apiVersion: v1
kind: Service
metadata:
  name: inventario-pt-service
spec:
  type: LoadBalancer
  selector:
    app: inventario-pt-app
    pod: inventario-pt-pod
  ports:
    - name: container-port
      targetPort: 80
      port: 80
```
como se observaen la linea 8 - 7 esté servicio ya esta vinculado por medio de los selectores **app**, **pod** con el deploymen del paso anterior, el servicio estará expuesto por el puerto 80, para desplegar esté archivo de configuración se usa el siguiente comando:

```docker
kubectl apply -f service.yml
```

este servicio crear un LoadBalancer que gestionara las peticiones y las redigira a los pods.

un vez este desplegado el servicio vamos a ejecutar el sigueinte comando para validar el resultado y ver el enpoint (EXTERNAL-IP) para acceder a nuestro servicio :


```docker
kubectl get svc -o wide
```
##Diseño de la solución
###Diagrama de componentes

###Diagrama de Secuencias

###Clases
###Arquitectura de la solución

##Swagger Local
- Para acceder al swuagger ejecute el **http://localhost/swagger-ui/index.html**

