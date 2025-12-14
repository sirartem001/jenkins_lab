pipeline {
    agent any

    stages {
        stage('Git Clone Ansible Repo') {
            steps {
                git url: 'https://github.com/sirartem001/ansible_deploy.git', branch: 'main'
            }
        }
        
        stage('Run Ansible Playbook'){
            steps {
                sh 'ansible-playbook -i inventory.ini playbook.yml'
            }
        }
    }
}