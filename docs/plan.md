# k8s platform Project

## Requirements

You will design and develop a cloud-based Kubernetes SaaS service that enables users to create Kubernetes clusters, deploy applications, and host services. Rules to follow:

1. Use AWS as the cloud service provider.
2. Only EC2 can be used as the computing service.
3. Choose the smallest AWS resources available and inform the channel.
4. Start with project planning to produce a list of tasks required to move the project forward.

## Use Case

- as a devops, I could create cluster from platform
- as a devops, I could get cluster info from platform
- as a developer, I could deploy appliations from platform
- as a developer, I could get deployment status of application from platform

## Architecture Design

### Workflow

![Activity](Activity.png)

### Domain Model

![Domain Model](Domain%20Model.png)

- Cluster, represent nodes in a group, it will be used in deployment.
- ClusterConfig, cluster config for specific k8s platform, related info is from Cluster
- Deployment, represent a flow of application deployment, it will use nodes from specific Cluster.
- DeploymentConfig, deployment config for specific k8s platform, related info is from Deployment

### Component Interaction

![interaction](interaction.png)

- Deployment Platform, take charge of interaction with k8s
- Monitor, take charge of getting status of Cluster/Deployment and update to platform.

## Preparation

- k8s envrionment
- frontend and backend init project

## Plan schedule

Backlog list below, the details will be mentioned in its own page,
according to priority order from high to low:

1. DevOps set up k8s environment
2. Dev set up init project, include frontend and backend
3. User can create Cluster in platform
4. User can see list of Cluster infos, include current status
5. User can create Deployment in platform
6. User can see list of Deployment infos, include current status
7. integrate basic login support
8. integrate RBAC for user
9. assign permission to APIs accorind to design
