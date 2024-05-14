# Edu+ Backend🧑‍💻📚🏫

Batch - Y3 S2

![GitHub Workflow Status](https://img.shields.io/github/workflow/status/user/repository/CI?style=flat-square)
![GitHub last commit](https://img.shields.io/github/last-commit/user/repository?style=flat-square)
![GitHub](https://img.shields.io/github/license/user/repository?style=flat-square)

---

## Team Members 🤝

- **Group Leader:** IT21277054 - Shiraz M.S (IT21277054)
- **Member 2:** IT21258794 - Karunasena H.G.M.K.K.L (IT21258794)
- **Member 3:** IT21222672 - Hansani K.J.M (IT21222672)
- **Member 4:** IT21174308 - Kumarasinghe O.A (IT21174308)

## Project Description 📝
EduPlus: Your Ultimate Git Learning Hub. Explore a variety of courses, from beginner to advanced, all dedicated to mastering Git repositories. With expert-led tutorials, interactive exercises, and a supportive community, EduPlus ensures you gain Git expertise efficiently. Join today and unlock your full potential in version control.

### Technologies used 💻
- React TS
- Spingboot
- mySql
- matirial

### Deployment commands
- in every service directory run, this will create tghe docker image and push it to docker hub
      ![!TIP] mvn clean package dockerfile:push 
- run this the see all the images that created
      ![!TIP] docker images
- run this in kubernetes directory in each sevice to deploy the docker container
      ![!TIP] kubectl apply -f ./
- run this for get the services wich are deployed
      ![!TIP] kubectl get services
- run this to map local port to remort port
      ![!TIP] kubectl port-forward service/<service-name> <local-port>:<remote-port>
