document.addEventListener("DOMContentLoaded", function () {
  var container = document.getElementById("map");
  var options = {
    center: new kakao.maps.LatLng(37.5665, 126.978),
    level: 4,
  };
  var map = new kakao.maps.Map(container, options);

  var clusterer = new kakao.maps.MarkerClusterer({
    map: map,
    averageCenter: true,
    minLevel: 6,
  });

  let rackTotCnt = [];
  let stationName = [];
  let parkingBikeTotCnt = [];
  let shared = [];
  let stationLatitude = [];
  let stationLongitude = [];

  const serviceKey = "5879694774706f6937326751565478";
  var xurl = `http://openapi.seoul.go.kr:8088/${serviceKey}/json/bikeList/1/1000/`;
  var url = `http://openapi.seoul.go.kr:8088/${serviceKey}/json/bikeList/1001/2000/`;

  function handleResponse(responseData) {
    if (responseData.rentBikeStatus && responseData.rentBikeStatus.row) {
      var bikeListArray = responseData.rentBikeStatus.row;
      bikeListArray.forEach((item) => {
        rackTotCnt.push(item.rackTotCnt);
        stationName.push(item.stationName);
        parkingBikeTotCnt.push(item.parkingBikeTotCnt);
        shared.push(item.shared);
        stationLatitude.push(item.stationLatitude);
        stationLongitude.push(item.stationLongitude);
      });
    } else {
      console.error("bikeList가 응답에 없거나 배열이 아닙니다.");
    }
  }

  var xhr1 = new XMLHttpRequest();
  xhr1.open("GET", xurl, true);
  xhr1.onreadystatechange = function () {
    if (
      this.readyState === XMLHttpRequest.DONE &&
      (xhr1.status === 200 || xhr1.status === 201)
    ) {
      var responseData = JSON.parse(this.responseText);
      handleResponse(responseData);

      var xhr2 = new XMLHttpRequest();
      xhr2.open("GET", url, true);
      xhr2.onreadystatechange = function () {
        if (
          this.readyState === XMLHttpRequest.DONE &&
          (xhr2.status === 200 || xhr2.status === 201)
        ) {
          var responseData2 = JSON.parse(this.responseText);
          handleResponse(responseData2);

          createCards();
          addMarkers();
        } else if (this.readyState === XMLHttpRequest.DONE) {
          console.error("Error fetching data: ", this.status);
        }
      };
      xhr2.send();
    } else if (this.readyState === XMLHttpRequest.DONE) {
      console.error("Error fetching data: ", this.status);
    }
  };
  xhr1.send();

  // 마커 생성
  function addMarkers() {
    var markers = [];
    for (let i = 0; i < rackTotCnt.length; i++) {
      var marker = new kakao.maps.Marker({
        position: new kakao.maps.LatLng(stationLatitude[i], stationLongitude[i]),
        title: stationName[i],
        desc: parkingBikeTotCnt[i]
      });

      var overlay = new kakao.maps.CustomOverlay({
        content: `<div class="wrap"><div class="info"><div class="title">${stationName[i].replace(/^\d+\.\s*/, "")}</div>
        <div class="body"><div class="desc"><div class="ellipsis">자전거 주차 총 건수: ${parkingBikeTotCnt[i]}</div></div></div></div>
        </div>`,map: null, // 초기에는 지도에 표시하지 않음
        position: marker.getPosition(),
            yAnchor: 1.5
      });
      kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, overlay));
      kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(overlay));

      markers.push(marker); // 생성된 마커를 배열에 추가
    }
    clusterer.addMarkers(markers); // 클러스터러에 마커들을 추가
  }

  // 인포윈도우를 표시하는 클로저를 만드는 함수
  function makeOverListener(map, marker, overlay) {
    return function () {
      overlay.setMap(map);
    };
  }

  // 인포윈도우를 닫는 클로저를 만드는 함수
  function makeOutListener(overlay) {
    return function () {
      overlay.setMap(null);
    };
  }


  var ps = new kakao.maps.services.Places();

  function SearchMap(event) {
    event.preventDefault();
    let searchAddress = document.getElementById("search-input").value;

    ps.keywordSearch(searchAddress, function (result, status) {
      if (status === kakao.maps.services.Status.OK) {
        const seoulResult = result.find((place) =>
          place.address_name.includes("서울")
        );

        if (seoulResult) {
          const coords = new kakao.maps.LatLng(seoulResult.y, seoulResult.x);
          map.panTo(coords);
          updateCards(coords);
        } else {
          alert("서울시 아닙니다.");
        }
      } else {
        alert("주소를 찾을 수 없습니다.");
      }
    });
  }

  window.SearchMap = SearchMap;

  function updateCards(coords) {
    const filteredIndexes = [];
    const radius = 0.01;

    for (let i = 0; i < stationName.length; i++) {
      const distance = Math.sqrt(
        Math.pow(stationLatitude[i] - coords.getLat(), 2) +
        Math.pow(stationLongitude[i] - coords.getLng(), 2)
      );

      if (distance < radius) {
        filteredIndexes.push(i);
      }
    }

    if (filteredIndexes.length > 0) {
      createCards(filteredIndexes);
    } else {
      displayNoResults();
    }
  }

  function createCards(filteredIndexes = []) {
    const cardContainer = document.getElementById("card-container");

    cardContainer.innerHTML = "";

    const indexes =
      filteredIndexes.length > 0
        ? filteredIndexes
        : Array.from(Array(stationName.length).keys());

    let row;

    for (let j = 0; j < indexes.length; j++) {
      const i = indexes[j];

      if (j % 2 === 0) {
        row = document.createElement("div");
        row.className = "row";
        cardContainer.appendChild(row);
      }

      const cardCol = document.createElement("div");
      cardCol.className = "col-md-6 mb-4";

      const progressBar =
        shared[i] < 50
          ? `<div class="progress" role="progressbar" aria-label="Danger example" aria-valuenow="${shared[i]}" aria-valuemin="0" aria-valuemax="100">
           <div class="progress-bar bg-danger" style="width: ${shared[i]}%"> ${shared[i]}%</div>
         </div>`
          : shared[i] < 100
            ? `<div class="progress" role="progressbar" aria-label="Warning example" aria-valuenow="${shared[i]}" aria-valuemin="0" aria-valuemax="100">
           <div class="progress-bar bg-warning" style="width: ${shared[i]}%"> ${shared[i]}% </div>
         </div>`
            : `<div class="progress" role="progressbar" aria-label="Basic example" aria-valuenow="${shared[i]}" aria-valuemin="0" aria-valuemax="100">
           <div class="progress-bar" style="width: ${shared[i]}%"> ${shared[i]}% </div>
         </div>`;

      const card = `
        <div class="card">
          <div class="card-body">
            <h4>${stationName[i].replace(/^\d+\.\s*/, "")}</h4>
            <p>거치대 개수: ${rackTotCnt[i]}</p>
            <p>자전거 주차 총 건수: ${parkingBikeTotCnt[i]}</p>
            <p>거치율</p>
            ${progressBar}
          </div>
        </div>
      `;
      cardCol.innerHTML = card;
      row.appendChild(cardCol);
    }
  }

  function displayNoResults() {
    const cardContainer = document.getElementById("card-container");
    cardContainer.innerHTML = "<p>해당 위치 근처에 자전거 대여소가 없습니다.</p>";
  }
});