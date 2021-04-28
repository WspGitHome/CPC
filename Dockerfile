FROM envoyproxy/envoy:latest
COPY envoy.yaml /etc/envoy/envoy.yaml
COPY search.pb /tmp/envoy/search.pb
