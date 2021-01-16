# simple_scheduler_play

- https://gentle-dawn-73192.herokuapp.com/
- Play Frameworkとデータベースの学習として、予定表/Todoリストアプリを作成した。

## 学んだこと

- Play FrameworkによるWebアプリケーション開発のキホンのキ。
- HTML/CSSかじり。(Bootstrapでごまかし。)  
- MySQLやPostgreSQLの基本操作。
- Webアプリケーションとデータベースの接続。
    - scalikejdbcを利用。
- Herokuへのデプロイ、Heroku上のPostgreSQL databaseへの接続。
- Heroku CLI操作。

## 要件

- [dwango テキスト](https://hexx.github.io/scala_text/introduction-of-web-application-2nd-day.html)
  より。

予定表のアプリケーションを作成してみましょう。作成する予定表には以下の機能があります。

予定の作成をする
指定した日の予定の一覧を見る
予定は以下の情報が含まれます。

予定の名前
予定の開始日時
予定の終了日時
予定には以下の制約があります。

予定の名前は1文字から30文字まで
予定の終了日時は予定の開始日時より後でなければならない
予定表には以下のページがあります。

トップ画面

http://localhost:9000/
クエリパラメーターがない場合→今日の予定を表示します
date=yyyy-mm-ddのようなクエリパラメーターがある場合→その日の予定を表示します

予定投稿画面

http://localhost:9000/add
予定を投稿できます
投稿された予定が制約を満たしていない場合はエラー表示をしてください
機能が完成したらPlayのSeleniumを使ったテストを作成してみましょう。