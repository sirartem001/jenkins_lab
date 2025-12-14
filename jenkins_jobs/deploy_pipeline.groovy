pipeline {
    agent any

    stages {
        stage('Git Clone Ansible Repo') {
            steps {
                git url: 'https://github.com/your-username/ansible-repo.git', branch: 'main'
            }
        }
        
        stage('Run Ansible Playbook'){
            steps {
                sh 'ansible-playbook -i inventory.ini playbook.yml'
            }
        }
    }
}