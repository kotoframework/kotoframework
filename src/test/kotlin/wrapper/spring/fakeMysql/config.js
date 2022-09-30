module.exports = {
    host: 'localhost',
    user: 'root',
    password: 'Leinbo2103221541@',
    database: 'koto_test',
    local: 'zh_CN',
    port: '3306',
    mode: 'pord',

    tables: [
        {
            name: 'tb_user',
            length: 500,
            data: {
                user_name: 'internet.userName',
                nickname: 'attach.name',
                password: 'internet.password',
                age: 'attach.age',
                phone_number: 'attach.phone',
                email_address: 'attach.email',
                birthday: 'attach.date',
                avatar: 'attach.avatar',
                sex: {
                    type: 'custom.sex',
                    filter() {
                        return Math.random() > 0.5 ? 'male' : 'female'
                    }
                },
                create_time: 'attach.datetime',
                update_time: 'attach.datetime',
            }
        }
    ]

}
