Pod::Spec.new do |s|
  s.name             = 'Pitaya'
  s.version          = '2.0.1'
  s.summary          = 'The pod for the pitaya client'

  s.description      = <<-DESC
    The missing pod for libpitaya with ssl support!
  DESC

  s.homepage         = 'https://github.com/taledog/libpitaya'
  s.license          = { :type => 'MIT', :file => 'LICENSE' }
  s.author           = { 'Arden' => 'arden@tujiao.com' }
  s.source           = { :git => 'https://github.com/taledog/libpitaya.git', :tag => s.version.to_s }

  s.ios.deployment_target = '8.0'

  s.source_files = ['src/**/*', 'include/**/*', 'cs/contrib/*']

  s.dependency 'OpenSSL-TFG', '~> 1.1.1b' 
  s.dependency 'libuv', '~> 1.4.0'

end
