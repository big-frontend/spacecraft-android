FROM python:3.7.3

WORKDIR /viserver

# Copy src files
COPY . .
#ADD . /SpacecraftServer4Flask

# Install depsrequirements.txt
#RUN   pip3 install pipenv && pipenv install
#RUN pip3 install -r requirements.txt -i https://pypi.tuna.tsinghua.edu.cn/simple
RUN pip3 install -r requirements.txt
# Expose port 5000
EXPOSE 9000

# Start the server
ENTRYPOINT [ "bash", "bootstrap"]