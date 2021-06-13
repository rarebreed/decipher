# Installs dependencies for script

if [[ -e ~/.pyenv ]]; then
  echo ".pyenv already exists"
else
  curl https://pyenv.run | sh
  exec $SHELL

  pyenv update
  pyenv i
fi