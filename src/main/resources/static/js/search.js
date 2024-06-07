function selectElement(selectedElement){
    addDataToForm(selectedElement);
    window.close();
}

function addDataToForm(ele) {
    console.log('addDataToForm' + ele);

    let elements = ele.getElementsByTagName("td");
    let data = {
        title: elements[0].innerText,
        author: elements[1].innerText,
        publishYear: elements[2].innerText,
        description: elements[4].innerText,
        imageUrl: elements[3].querySelector('img').getAttribute('src')
    };

    opener.document.getElementById("add_form_title").value = data.title;
    opener.document.getElementById("add_form_author").value = data.author;
    opener.document.getElementById("add_form_publishYear").value = data.publishYear;
    opener.document.getElementById("add_form_description").value = data.description;
    opener.document.getElementById("add_form_imageUrl").value = data.imageUrl;
}