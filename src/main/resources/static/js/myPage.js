document.addEventListener('DOMContentLoaded', (event) => {
    showSection('posts');
});

function showSection(sectionId) {
    const sections = document.querySelectorAll('.section');
    sections.forEach(section => {
        section.classList.remove('active');
    });

    const activeSection = document.getElementById(sectionId);
    activeSection.classList.add('active');
}

function truncateContent() {
    const contents = document.querySelectorAll('.post-content, .comment-content');
    contents.forEach(content => {
        if (content.textContent.length > 75) {
            content.textContent = content.textContent.slice(0, 75) + '...';
        }
    });
}