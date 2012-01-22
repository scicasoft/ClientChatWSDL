require 'rubygems'
require 'sinatra'
require 'soap/rpc/driver'
require 'erb'
require 'sass'

enable :sessions

NAMESPACE = 'ChatRoom'
URL = 'http://localhost:8080/axis/services/ChatRoom.jws?wsdl'
driver = SOAP::RPC::Driver.new(URL, NAMESPACE)
driver.add_method('getUsersOnline')
driver.add_method('sendMessage', 'id_sender', 'id_receiver', 'message')
driver.add_method('getFirstMessages', 'id', 'id_sender')
driver.add_method('login', 'id')
driver.add_method('logout', 'id')

get '/' do
  redirect '/chat' unless session[:chat_id].nil?
  erb :index
end

get '/send_message/:id' do
  driver.sendMessage(session[:chat_id], params[:id], params[:message].split('%20').join(' '))
  ""
end

get '/message/:id' do
  driver.getFirstMessages(session[:chat_id], params[:id])
end

post '/chat' do
  session[:chat_id] = params[:id]
  @id = session[:chat_id]
  
  driver.login(@id)
  
  erb :chat
end

get '/chat' do
  @id = session[:chat_id]
  
  driver.login(@id)
  
  erb :chat
end

get '/chat_avec/:id' do
  @id = session[:chat_id]
  @client = params[:id]
  
  erb :chat_avec
end

get '/logout' do
  driver.logout(session[:chat_id])
  
  session[:chat_id] = nil
  redirect '/'
end

get '/utilisateurs_en_ligne' do
  @clients = driver.getUsersOnline.split(':').reverse
  @clients.pop
  erb :utilisateurs_en_ligne
end

get '/style.css' do
  sass :style
end
